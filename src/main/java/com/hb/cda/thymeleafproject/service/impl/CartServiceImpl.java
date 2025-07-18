package com.hb.cda.thymeleafproject.service.impl;


import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.service.Cart;
import com.hb.cda.thymeleafproject.service.CartSerializer;
import com.hb.cda.thymeleafproject.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Service
public class CartServiceImpl implements CartService {
    
    private final ProductRepository productRepository;
    private final CartSerializer cartSerializer;

    public CartServiceImpl(ProductRepository productRepository, CartSerializer cartSerializer) {
        this.productRepository = productRepository;
        this.cartSerializer = cartSerializer;
    }


    @Override
    public Cart getCart(HttpServletRequest request) {
        return cartSerializer.deserializeCart(request);
    }


    @Override
    public void addProductInCart(Product product, int qty, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = this.getCart(request);
        cart.addProduct(product, qty);
        cartSerializer.serializeCart(cart, response);
    }


    @Override
    public void diminishQuantityInCart(Product product, int qty, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = this.getCart(request);
        cart.decreaseProductQuantity(product, qty);
        cartSerializer.serializeCart(cart, response);
    }


    @Override
    public void removeProductFromCart(Product product, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = this.getCart(request);
        cart.removeProduct(product);
        cartSerializer.serializeCart(cart, response);
    }


    @Override
    public void removeEntireCart(HttpServletResponse response) {
        cartSerializer.deleteCartCookie(response);
    }


    @Override
    public double getTotalPrice(HttpServletRequest request) {

        Cart cart = this.getCart(request);
        double sum = 0.0;
        for(HashMap.Entry<String, Integer> item : cart.getItems().entrySet() ) {
            Product product = productRepository.findById(item.getKey())
                .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            sum += item.getValue() * product.getPrice();
        }
        return sum;

    }


    public ProductRepository getProductRepository() {
        return productRepository;
    }
    
}
