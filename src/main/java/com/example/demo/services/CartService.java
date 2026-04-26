package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.CartItem;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.CartItemRepository;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Get all cart items for a user.
     */
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    /**
     * Add a product to the user's cart (or increment quantity if already exists).
     */
    public void addToCart(User user, Product product, int quantity) {
        Optional<CartItem> existing = cartItemRepository.findByUserAndProduct(user, product);

        if (existing.isPresent()) {
            // Product already in cart — increase quantity
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            // New cart item
            CartItem item = new CartItem(user, product, quantity);
            cartItemRepository.save(item);
        }
    }

    /**
     * Update quantity for a specific cart item.
     */
    public void updateQuantity(int cartItemId, int quantity) {
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if (optional.isPresent()) {
            CartItem item = optional.get();
            if (quantity <= 0) {
                cartItemRepository.delete(item);
            } else {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            }
        }
    }

    /**
     * Remove a specific item from cart.
     */
    public void removeFromCart(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    /**
     * Clear all items from a user's cart.
     */
    @Transactional
    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }

    /**
     * Calculate the total price of items in the cart.
     */
    public double getCartTotal(User user) {
        List<CartItem> items = getCartItems(user);
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    /**
     * Get total number of items in the cart.
     */
    public int getCartCount(User user) {
        List<CartItem> items = getCartItems(user);
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
