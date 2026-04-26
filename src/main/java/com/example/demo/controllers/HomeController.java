package com.example.demo.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Product;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.services.ProductServices;

@Controller
public class HomeController  {
	
	@Autowired
	private ProductServices productServices;

	@Autowired
	private com.example.demo.services.UserServices userServices;
	
	@GetMapping(value = {"/home", "/"})
	public String home() {
		return "Home";
	}

	@GetMapping("/products")
	public String products( Model model) { 
		List<Product> allProducts = this.productServices.getAllProducts();
		model.addAttribute("products", allProducts);
		return "Products";
	}

	@GetMapping("/location")
	public String location() {
		return "Locate_us";
	}

	@GetMapping("/about")
	public String about() {
		return "About";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("adminLogin",new AdminLogin());
		return "Login";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("userRegistration", new com.example.demo.entities.User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@org.springframework.web.bind.annotation.ModelAttribute("userRegistration") com.example.demo.entities.User user, Model model) {
		try {
			// addUser encodes the password via BCrypt
			userServices.addUser(user);
			return "redirect:/login?registered=true";
		} catch (Exception e) {
			model.addAttribute("error", "Email already exists or invalid data provided.");
			return "register";
		}
	}

}