package com.hb.cda.thymeleafproject.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CartSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();


    public void serializeCart(Cart cart, HttpServletResponse response) {

        try {
            String cartJson = objectMapper.writeValueAsString(cart.getItems());
            String encoded = URLEncoder.encode(cartJson, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("cart", encoded);
            cookie.setPath("/");
            cookie.setMaxAge(28800); 
            response.addCookie(cookie);
        } catch(IOException e) {
            throw new RuntimeException("Erreur de sérialisation du panier", e);
        }
    }


    public Cart deserializeCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return new Cart(); 
        }

        for (Cookie cookie : cookies) {
            if ("cart".equals(cookie.getName())) {
                try {
                    String json = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);

                    Map<String, Integer> items = objectMapper.readValue(
                        json, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Integer>>() {}
                    );

                    Cart cart = new Cart();
                    cart.setItems(items);

                    return cart;

                } catch (IOException e) {
                    throw new RuntimeException("Erreur de désérialisation du panier", e);
                }
            }
        }

        return new Cart(); 
    }

    public void deleteCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
