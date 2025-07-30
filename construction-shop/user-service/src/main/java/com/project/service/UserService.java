package com.project.service;

import com.project.dto.RegisterRequest;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Пользователь с таким email или username уже существует");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // позже добавим хэширование
        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    public Optional<User> getUserById (Long id){
        return userRepository.findById(id);
    }

    public void delete (Long id){
        userRepository.deleteById(id);
    }

    public List<User> getAll () {
        return userRepository.findAll();
    }

    // Остальные методы (поиск, проверка и т.д.)
}
