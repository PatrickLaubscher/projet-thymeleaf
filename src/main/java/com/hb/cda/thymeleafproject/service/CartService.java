package com.hb.cda.thymeleafproject.service;

import com.hb.cda.thymeleafproject.entity.Product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public interface CartService {

    // get the cart from session variables
    public Cart getCart(HttpServletRequest request);

    // add product in cart
    public void addProductInCart(Product product, int qty, HttpServletRequest request, HttpServletResponse response);

    // diminish quantities in cart
    public void diminishQuantityInCart(Product product, int qty, HttpServletRequest request, HttpServletResponse response);

    // remove product from cart
    public void removeProductFromCart(Product product, HttpServletRequest request, HttpServletResponse response);

    // remove cart entirely
    public void removeEntireCart(HttpServletResponse response);

    // calculate and display total price
    public double getTotalPrice(HttpServletRequest request);

    // validate cart, empty localvariable and insert into ProductInCart table



}
