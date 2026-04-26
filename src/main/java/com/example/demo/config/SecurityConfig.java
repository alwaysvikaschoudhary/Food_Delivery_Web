package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // ===== BCrypt Password Encoder =====
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ===== In-Memory Admin UserDetailsService =====
    @Bean(name = "adminUserDetailsService")
    public UserDetailsService adminUserDetailsService() {
        UserDetails admin = User.builder()
                .username(adminEmail)
                .password(passwordEncoder().encode(adminPassword))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    // ===== AuthenticationManager for Admin (in-memory) =====
    @Bean(name = "adminAuthenticationManager")
    public AuthenticationManager adminAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    // ===== AuthenticationManager for User (database) — marked as primary =====
    @Primary
    @Bean(name = "userAuthenticationManager")
    public AuthenticationManager userAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    // ===== Security Filter Chain =====
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF is enabled by default — Thymeleaf handles tokens via th:action

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Public pages
                .requestMatchers(
                    "/", "/home", "/products", "/about", "/location",
                    "/login", "/register",
                    "/adminLogin", "/userLogin",
                    "/css/**", "/js/**", "/Images/**", "/images/**",
                    "/favicon.ico"
                ).permitAll()

                // Admin-only pages
                .requestMatchers(
                    "/admin/**",
                    "/addAdmin", "/addProduct", "/addUser",
                    "/addingAdmin", "/products/add", "/addingUser",
                    "/updateAdmin/**", "/updatingAdmin/**",
                    "/updateProduct/**", "/updatingProduct/**",
                    "/updateUser/**", "/updatingUser/**",
                    "/deleteAdmin/**", "/deleteProduct/**", "/deleteUser/**"
                ).hasRole("ADMIN")

                // User-only pages (Cart + Order + legacy product flow)
                .requestMatchers(
                    "/cart", "/cart/**",
                    "/order/**",
                    "/product/search", "/product/order", "/product/back"
                ).hasRole("USER")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // Custom login page
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // Session management
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            );

        return http.build();
    }
}
