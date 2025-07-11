package com.hb.cda.thymeleafproject.service.impl;


import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.service.Cart;
import com.hb.cda.thymeleafproject.service.CartService;





public class CartServiceImpl implements CartService {


    @Override
    public Cart getCart(HttpSession session) {

        Cart cart = (Cart) session.getAttribute("panier");;
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
    public double getTotalPrice(HttpSession session) {

        Cart cart = this.getCart(session);
        


    }
    
}
