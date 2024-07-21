package com.app.aspire.customerloan.configs;

import com.app.aspire.customerloan.entities.Users;
import com.app.aspire.customerloan.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class InitialDataLoader {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner loadInitialData(PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if users already exist
            if (userRepository.count() == 0) {
                // Create dummy users
                Users user1 = new Users();
                user1.setUsername("user1");
                user1.setPassword(passwordEncoder.encode("password1"));
                user1.setRole("ROLE_CUSTOMER");

                Users user2 = new Users();
                user2.setUsername("user2");
                user2.setPassword(passwordEncoder.encode("password2"));
                user2.setRole("ROLE_CUSTOMER");

                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("ROLE_CUSTOMER,ROLE_ADMIN");

                // Save users
                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(admin);
            }
        };
    }
}