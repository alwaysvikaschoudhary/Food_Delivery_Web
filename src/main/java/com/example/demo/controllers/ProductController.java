package com.example.demo.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;
import com.example.demo.services.ImageUploadService;

@Controller
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@Autowired
	private ImageUploadService imageUploadService;

	@PostMapping("/products/add")
	public String addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile image, Model model) {
		try {
			if (image != null && !image.isEmpty()) {
				String imageUrl = imageUploadService.uploadImage(image);
				product.setImageUrl(imageUrl);
			}
			this.productServices.addProduct(product);
			return "redirect:/admin/services";
		} catch (Exception e) {
			model.addAttribute("product", product);
			model.addAttribute("errorMessage", "Upload Failed: " + e.getMessage() + (e.getCause() != null ? " (" + e.getCause().getMessage() + ")" : ""));
			return "Add_Product";
		}
	}

	@PostMapping("/updatingProduct/{productId}")
	public String updateProduct(@ModelAttribute Product product, @PathVariable("productId") int id, @RequestParam(value = "image", required = false) MultipartFile image, Model model) {
		try {
			if (image != null && !image.isEmpty()) {
				String imageUrl = imageUploadService.uploadImage(image);
				product.setImageUrl(imageUrl);
			} else {
				Product existingProduct = productServices.getProduct(id);
				if (existingProduct != null) {
					product.setImageUrl(existingProduct.getImageUrl());
				}
			}
			this.productServices.updateproduct(product, id);
			return "redirect:/admin/services";
		} catch (Exception e) {
			model.addAttribute("product", product);
			model.addAttribute("errorMessage", "Upload Failed: " + e.getMessage() + (e.getCause() != null ? " (" + e.getCause().getMessage() + ")" : ""));
			return "Update_Product";
		}
	}

	@GetMapping("/deleteProduct/{productId}")
	public String delete(@PathVariable("productId") int id) {
		this.productServices.deleteProduct(id);
		return "redirect:/admin/services";
	}
	
}