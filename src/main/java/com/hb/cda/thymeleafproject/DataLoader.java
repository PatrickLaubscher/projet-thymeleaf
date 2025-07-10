package com.hb.cda.thymeleafproject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public DataLoader(UserRepository userRepository, PasswordEncoder encoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadProducts();
    }

    private void loadUsers() {
        
        if(userRepository.findByUsername("Henry").isEmpty()) {
            User user1 = new User("Henry", encoder.encode("henry"), "ROLE_USER");
            userRepository.save(user1);
        }

    }

    private void loadProducts() {

        List<Product> productsList = List.of(
                new Product("pomme", 1.0, "pomme rouge", 20),
                new Product("poire", 1.2, "poire jaune", 30),
                new Product("orange", 1.4, "orange", 25)
        );

        for (Product product: productsList) {
            if(productRepository.findByName(product.getName()).isEmpty()){
                productRepository.save(product);
            }
        }

    }
     
}
