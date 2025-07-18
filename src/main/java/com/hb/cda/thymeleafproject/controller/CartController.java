package com.hb.cda.thymeleafproject.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.hb.cda.thymeleafproject.entity.Order;
import com.hb.cda.thymeleafproject.entity.OrderItem;
import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.OrderItemRepository;
import com.hb.cda.thymeleafproject.repository.OrderRepository;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;
import com.hb.cda.thymeleafproject.service.Cart;
import com.hb.cda.thymeleafproject.service.impl.CartServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/cart")
public class CartController {


    private final CartServiceImpl cartService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    public CartController(CartServiceImpl cartService, ProductRepository productRepository,
            OrderItemRepository orderItemRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("")
    public String displayCartList(Model model, HttpSession session, @AuthenticationPrincipal UserDetails userDetails) {

        Cart cart = cartService.getCart(session);

        Map<Product, Integer> cartItems = new HashMap<>();
        for(HashMap.Entry<String, Integer> item : cart.getItems().entrySet() ) {
            Product product = productRepository.findById(item.getKey())
                .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
           cartItems.put(product, item.getValue());
        }

        Double totalPrice = cartService.getTotalPrice(session);

        if(userDetails != null && userDetails.isEnabled()) {
            model.addAttribute("username", userDetails.getUsername());
        }

        model.addAttribute("cart", cartItems);
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

    @PostMapping("/decrease-qty")
    public String decreaseQtyInCart(@ModelAttribute @Valid AddToCartDTO dto, BindingResult bindingResult, HttpSession session) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (product != null) {
            cartService.diminishQuantityInCart(product, dto.getQuantity(), session);
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
    

    @Transactional
    @PostMapping("/validate-cart")
    public String validateCart(@AuthenticationPrincipal UserDetails userDetails, HttpSession session) {

        if(userDetails == null) {
            return "redirect:/login";
        }

        User customer = userRepository.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        Cart cart = cartService.getCart(session);

        if(cart.getTotalQuantity() == 0) {
            return "redirect:/cart";
        }

        LocalDateTime orderDate = LocalDateTime.now();
        

        Order order = new Order(orderDate, cartService.getTotalPrice(session), customer);
        orderRepository.save(order);

        Map<Product, Integer> cartItems = new HashMap<>();
        for(HashMap.Entry<String, Integer> item : cart.getItems().entrySet() ) {
            Product product = productRepository.findById(item.getKey())
                .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

           cartItems.put(product, item.getValue());
        }

        for(Map.Entry<Product, Integer> item : cartItems.entrySet()) {

            Product product = item.getKey();

            if (product.getStock() < item.getValue()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuffisant pour " + product.getName());
            }

            // create new order item
            OrderItem newOrderItem = new OrderItem(item.getValue(), item.getKey(), order);
            orderItemRepository.save(newOrderItem);

            // diminish stock in product list
            Integer newStock = product.getStock() - item.getValue();
            product.setStock(newStock);
            productRepository.save(product);
        }

        // remove cart
        cartService.removeEntireCart(session);
        
        return "redirect:/cart";
    }

}
