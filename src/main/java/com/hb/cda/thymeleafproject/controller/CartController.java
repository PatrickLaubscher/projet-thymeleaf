package com.hb.cda.thymeleafproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.hb.cda.thymeleafproject.dto.AddToCartDTO;
import com.hb.cda.thymeleafproject.dto.RemoveFromCartDTO;
import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.service.Cart;
import com.hb.cda.thymeleafproject.service.impl.CartServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/cart")
public class CartController {


    private final CartServiceImpl cartService;
    private final ProductRepository productRepository;


    public CartController(CartServiceImpl cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }


    @GetMapping("")
    public String displayCartList(Model model, HttpSession session) {

        Cart cart = cartService.getCart(session);
        Double totalPrice = cartService.getTotalPrice(session);

        model.addAttribute("cart", cart); 
        model.addAttribute("total", totalPrice); 
        model.addAttribute("addToCartDTO", new AddToCartDTO());
        model.addAttribute("removeFromCartDTO", new RemoveFromCartDTO());

        return "cart";
    }

    
    @PostMapping("/add-to-cart")
    public String addProductInCart(@ModelAttribute @Valid AddToCartDTO dto, BindingResult bindingResult, HttpSession session) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (product != null) {
            cartService.addProductInCart(product, dto.getQuantity(), session);
        }
        
        return "redirect:/cart";
    }


    @PostMapping("/remove-from-cart")
    public String removeProductFromCart(@ModelAttribute @Valid RemoveFromCartDTO dto, BindingResult bindingResult, HttpSession session) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (product != null) {
            cartService.removeProductFromCart(product, session);
        }
        
        return "redirect:/cart";
    }


    @PostMapping("/empty-cart")
    public String emptyCart(HttpSession session) {

        cartService.removeEntireCart(session);
        
        return "redirect:/cart";
    }
    

}
