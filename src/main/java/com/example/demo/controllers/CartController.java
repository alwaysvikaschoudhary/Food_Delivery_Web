package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.CartItem;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.CartService;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private OrderServices orderServices;

    /**
     * Helper: get the current logged-in User entity from Spring Security context.
     */
    private User getLoggedInUser(Authentication authentication) {
        String email = authentication.getName();
        return userServices.getUserByEmail(email);
    }

    // ===== ADD TO CART =====
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Product product = productServices.getProduct(productId);
        cartService.addToCart(user, product, quantity);
        return "redirect:/cart";
    }

    // ===== VIEW CART =====
    @GetMapping("/cart")
    public String viewCart(Model model, Authentication authentication) {
        User user = getLoggedInUser(authentication);
        List<CartItem> cartItems = cartService.getCartItems(user);
        double cartTotal = cartService.getCartTotal(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("name", user.getUname());
        return "Cart";
    }

    // ===== UPDATE QUANTITY =====
    @PostMapping("/cart/update/{cartItemId}")
    public String updateQuantity(@PathVariable("cartItemId") int cartItemId,
                                  @RequestParam("quantity") int quantity) {
        cartService.updateQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }

    // ===== REMOVE ITEM =====
    @GetMapping("/cart/remove/{cartItemId}")
    public String removeItem(@PathVariable("cartItemId") int cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }

    // ===== PLACE ORDER (Checkout from Cart) =====
    @PostMapping("/order/place")
    public String placeOrder(Authentication authentication, Model model) {
        User user = getLoggedInUser(authentication);
        List<CartItem> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        double grandTotal = 0;

        // Create an Order for each cart item
        for (CartItem item : cartItems) {
            Orders order = new Orders();
            order.setoName(item.getProduct().getPname());
            order.setoPrice(item.getProduct().getPprice());
            order.setoQuantity(item.getQuantity());
            order.setTotalAmmout(item.getSubtotal());
            order.setUser(user);
            order.setOrderDate(new Date());
            orderServices.saveOrder(order);
            grandTotal += item.getSubtotal();
        }

        // Clear the cart after placing order
        cartService.clearCart(user);

        model.addAttribute("amount", grandTotal);
        return "Order_success";
    }

    // ===== ORDER HISTORY =====
    @GetMapping("/order/history")
    public String orderHistory(Model model, Authentication authentication) {
        User user = getLoggedInUser(authentication);
        List<Orders> orders = orderServices.getOrdersForUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("name", user.getUname());
        return "Order_History";
    }
}
