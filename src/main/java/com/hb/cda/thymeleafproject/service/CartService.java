package com.hb.cda.thymeleafproject.service;

import com.hb.cda.thymeleafproject.entity.Product;

import jakarta.servlet.http.HttpSession;



public interface CartService {
    
    // get the cart from session variables
    public Cart getCart(HttpSession session);

    // add product in cart
    public void addProductInCart(Product product, int qty, HttpSession session);

    // diminish quantities in cart
    public void diminishQuantityInCart(Product product, int qty, HttpSession session);

    // remove product from cart
    public void removeProductFromCart(Product product, HttpSession session);

    // remove cart entirely
    public void removeEntireCart(HttpSession session);

    // calculate and display total price
    public double getTotalPrice(HttpSession session);

    // validate cart, empty localvariable and insert into ProductInCart table



}
