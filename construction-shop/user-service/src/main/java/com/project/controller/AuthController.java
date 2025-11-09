package com.project.controller;

import com.project.dto.RegisterRequest;
import com.project.model.Order;
import com.project.model.Product;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.OrderRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    // ======= Показ формы регистрации =======
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register"; // шаблон register.html
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterRequest request, Model model, HttpSession session) {
        try {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                model.addAttribute("errorMessage", "Имя пользователя уже занято.");
                return "register";
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(Role.CUSTOMER);

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setMiddleName(request.getMiddleName());

            userRepository.save(user);
            session.setAttribute("user", user);

            return "redirect:/auth/profile";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при регистрации. Попробуйте ещё раз.");
            return "register";
        }
    }


    @GetMapping("/profile")
    public String userProfile(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return "redirect:/auth/login";
        }

        Optional<User> userOpt = userRepository.findUserWithOrders(sessionUser.getId());
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Пользователь не найден");
            return "error";
        }

        User user = userOpt.get();
        model.addAttribute("user", user);

        List<Order> orders = user.getOrders();
        for (Order order : orders) {
            double total = order.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
            order.setTotalAmount(total); // добавь поле totalAmount в Order
        }

        model.addAttribute("orders", orders);

        return "profile";
    }

    @PostMapping("/update-fio")
    public String updateFio(@ModelAttribute("user") User updatedUser, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/auth/login";

        if (updatedUser.getFirstName().isBlank() || updatedUser.getLastName().isBlank()) {
            model.addAttribute("errorMessage", "Имя и фамилия обязательны");
            model.addAttribute("user", sessionUser);
            return "profile";
        }

        sessionUser.setFirstName(updatedUser.getFirstName());
        sessionUser.setLastName(updatedUser.getLastName());
        sessionUser.setMiddleName(updatedUser.getMiddleName());

        userRepository.save(sessionUser);
        session.setAttribute("user", sessionUser);

        return "redirect:/auth/profile";
    }

    @GetMapping("/payment")
    public String paymentPage(@RequestParam("orderId") Long orderId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/auth/login";

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty() || !orderOpt.get().getUser().getId().equals(user.getId())) {
            model.addAttribute("errorMessage", "Заказ не найден или не принадлежит вам");
            return "error";
        }

        Order order = orderOpt.get();

        // 🔧 Вычисляем сумму заказа
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);

        model.addAttribute("order", order);
        return "payment";
    }


    @PostMapping("/payment/submit")
    public String submitPayment(@RequestParam Long orderId,
                                @RequestParam String cardNumber,
                                @RequestParam String expiryDate,
                                @RequestParam String cvv,
                                @RequestParam(required = false) String saveCard,
                                HttpSession session,
                                Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/auth/login";

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty() || !orderOpt.get().getUser().getId().equals(user.getId())) {
            model.addAttribute("errorMessage", "Ошибка оплаты: заказ не найден");
            return "error";
        }

        // 💳 Здесь можно добавить логику оплаты
        if ("on".equals(saveCard)) {
            user.setSavedCard(cardNumber); // добавь поле savedCard в User
            userRepository.save(user);
        }

        model.addAttribute("message", "Оплата прошла успешно!");
        return "payment-success";
    }


    // ======= Страница входа (заглушка) =======
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("message", "Это просто заглушка страницы входа");
        return "login"; // шаблон login.html
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Проверка пароля
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                return "redirect:/auth/profile";
            }
            else {
                model.addAttribute("error", "Неверный пароль");
                return "login";
            }
        } else {
            model.addAttribute("error", "Пользователь не найден");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/auth/home";
    }


    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user); // может быть null — и это хорошо

        return "home";
    }



    // ======= Страница заказа =======
    @GetMapping("/order")
    public String showOrderPage(Model model) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Кирпич", 100, 12.5));
        products.add(new Product("Цемент", 50, 8.0));
        products.add(new Product("Песок", 200, 3.5));

        Order order = new Order("г. Москва, ул. Примерная, д.1", "Ожидается", products);
        User user = new User("Иванов Иван Иванович", "ivan@example.com", Collections.singletonList(order));

        model.addAttribute("user", user);
        return "order";
    }
}
