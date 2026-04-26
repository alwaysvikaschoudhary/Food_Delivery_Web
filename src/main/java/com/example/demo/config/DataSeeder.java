package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                // Pre-populate database with default products to match the UI
                productRepository.save(createProduct("Chicken Biryani", 410, "Hyderabadi-style dum biryani with tender chicken"));
                productRepository.save(createProduct("Paneer Butter Masala", 290, "Rich creamy gravy with soft cottage cheese cubes"));
                productRepository.save(createProduct("Afgani Chicken", 410, "Creamy cashew-based curry with tender chicken"));
                productRepository.save(createProduct("Butter Chicken", 380, "Classic creamy tomato-based chicken curry"));
                productRepository.save(createProduct("Laccha Paratha", 30, "Flaky layered Indian bread, perfectly crispy"));
                productRepository.save(createProduct("Matka Chicken", 510, "Slow-cooked chicken in a traditional clay pot"));
                productRepository.save(createProduct("Chicken Tikka", 310, "Tandoori-grilled marinated chicken pieces"));
                productRepository.save(createProduct("Honey Chilli Potato", 150, "Crispy potatoes tossed in sweet chilli sauce"));
                productRepository.save(createProduct("Chola Bhatura", 180, "Spicy chickpea curry with fluffy fried bread"));
                productRepository.save(createProduct("Gulab Jamun", 50, "Sweet dumplings soaked in rose-flavored syrup"));
                productRepository.save(createProduct("Veg Chowmein", 150, "Stir-fried noodles with fresh vegetables"));
                productRepository.save(createProduct("Paneer Do Pyaza", 270, "Paneer cooked with double the onions in rich gravy"));
                System.out.println("Default products seeded into the database.");
            }
        };
    }

    private Product createProduct(String name, double price, String description) {
        Product p = new Product();
        p.setPname(name);
        p.setPprice(price);
        p.setPdescription(description);
        return p;
    }
}
