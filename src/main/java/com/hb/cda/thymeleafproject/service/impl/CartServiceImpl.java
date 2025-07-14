package com.hb.cda.thymeleafproject.service.impl;


import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.service.Cart;
import com.hb.cda.thymeleafproject.service.CartService;

import jakarta.servlet.http.HttpSession;


@Service
public class CartServiceImpl implements CartService {


    @Override
    public Cart getCart(HttpSession session) {

        Cart cart = (Cart) session.getAttribute("panier");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("panier", cart);
        }
        return cart;

    }


    @Override
    public void addProductInCart(Product product, int qty, HttpSession session) {

        Cart cart = this.getCart(session);
        cart.addProduct(product, qty);
    }


    @Override
    public void removeProductFromCart(Product product, HttpSession session) {

        Cart cart = this.getCart(session);
        cart.removeProduct(product);
    }


    @Override
    public void removeEntireCart(HttpSession session) {

        session.removeAttribute("panier");
    }


    @Override
    public double getTotalPrice(HttpSession session) {

        Cart cart = this.getCart(session);
        double sum = 0.0;
        for(HashMap.Entry<Product, Integer> item : cart.getItems().entrySet() ) {
            sum += item.getValue() * item.getKey().getPrice();
        }
        return sum;

    }
    
}
