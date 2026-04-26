package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartItemId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private int quantity;

	public CartItem() {}

	public CartItem(User user, Product product, int quantity) {
		this.user = user;
		this.product = product;
		this.quantity = quantity;
	}

	// Getters and Setters
	public int getCartItemId() { return cartItemId; }
	public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Product getProduct() { return product; }
	public void setProduct(Product product) { this.product = product; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	/**
	 * Returns total price for this cart item (price × quantity)
	 */
	public double getSubtotal() {
		return product.getPprice() * quantity;
	}

	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", product=" + product
				+ ", quantity=" + quantity + "]";
	}
}
