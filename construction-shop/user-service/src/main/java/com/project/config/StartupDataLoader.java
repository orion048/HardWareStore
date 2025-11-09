package com.project.config;

import com.project.model.Product;
import com.project.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupDataLoader {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initProducts() {
        System.out.println("StartupDataLoader запущен");
        long count = productRepository.count();
        System.out.println("Товаров в базе: " + count);


        if (productRepository.count() == 0) {
            System.out.println("Добавляем товары...");

            Product p1 = new Product("Цемент М500", 50, 450.00);
            p1.setImageUrl("/images/cement.jpeg");
            productRepository.save(p1);

            Product p2 = new Product("Кирпич красный полнотелый", 1000, 25.00);
            p2.setImageUrl("/images/brick.jpg");
            productRepository.save(p2);

            Product p3 = new Product("Арматура 12 мм", 200, 120.00);
            p3.setImageUrl("/images/rebar.jpg");
            productRepository.save(p3);

            Product p4 = new Product("Песок строительный (мешок 25 кг)", 80, 180.00);
            p4.setImageUrl("/images/sand.jpg");
            productRepository.save(p4);

            Product p5 = new Product("Щебень фракция 5–20 мм (мешок)", 60, 200.00);
            p5.setImageUrl("/images/gravel.jpg");
            productRepository.save(p5);

            Product p6 = new Product("Гипсокартон влагостойкий 12.5 мм", 100, 350.00);
            p6.setImageUrl("/images/drywall.jpg");
            productRepository.save(p6);

            Product p7 = new Product("Пена монтажная", 150, 250.00);
            p7.setImageUrl("/images/foam.jpeg");
            productRepository.save(p7);

            Product p8 = new Product("Шурупы универсальные (упаковка)", 300, 90.00);
            p8.setImageUrl("/images/screws.jpeg");
            productRepository.save(p8);

            Product p9 = new Product("Уровень строительный 60 см", 40, 600.00);
            p9.setImageUrl("/images/level.jpg");
            productRepository.save(p9);

            Product p10 = new Product("Лопата штыковая", 30, 700.00);
            p10.setImageUrl("/images/shovel.jpg");
            productRepository.save(p10);

            Product p11 = new Product("Перфоратор", 10, 8500.00);
            p11.setImageUrl("/images/perforator.png");
            productRepository.save(p11);

            Product p12 = new Product("Краска фасадная белая (10 л)", 25, 1200.00);
            p12.setImageUrl("/images/paint.jpg");
            productRepository.save(p12);

            Product p13 = new Product("Рулетка измерительная 5 м", 50, 250.00);
            p13.setImageUrl("/images/tape.jpg");
            productRepository.save(p13);

            Product p14 = new Product("Плитка керамическая 30x30 см", 70, 950.00);
            p14.setImageUrl("/images/tile.jpg");
            productRepository.save(p14);

            Product p15 = new Product("Лестница алюминиевая 3 м", 15, 3200.00);
            p15.setImageUrl("/images/ladder.jpg");
            productRepository.save(p15);

            System.out.println("Все товары успешно добавлены.");
        }

    }

}
