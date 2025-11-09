package com.project;

import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

/*
@Bean
CommandLineRunner test(UserRepository userRepository) {
	return args -> {
		User user = new User("runneruser", "runner@example.com", "123456", Role.CUSTOMER);
		userRepository.save(user);
		System.out.println(">>> User saved from CommandLineRunner");
	};
}
*/
}