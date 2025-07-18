package com.hb.cda.thymeleafproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.hb.cda.thymeleafproject.dto.AddToCartDTO;
import com.hb.cda.thymeleafproject.dto.ProductDTO;
import com.hb.cda.thymeleafproject.dto.ProductMapper;
import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.service.impl.CartServiceImpl;

import jakarta.servlet.http.HttpSession;





@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final CartServiceImpl cartService;
    private final ProductMapper productMapper;

    

    public ProductController(ProductRepository productRepository, CartServiceImpl cartService,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.productMapper = productMapper;
    }


    @GetMapping("")
    public String displayProductList(Model model, @RequestParam(defaultValue = "1") int pageNb) {

        Pageable firstPageWithTwoElements = PageRequest.of(pageNb-1, 5, Sort.by("name").descending());

        Page<Product> page = productRepository.findAll(firstPageWithTwoElements);

        Page<ProductDTO> productsList = page.map(productMapper::convertToDTO);


        model.addAttribute("pageNb", pageNb);
        model.addAttribute("products", productsList);
        model.addAttribute("addToCartDTO", new AddToCartDTO());

        return "product-list";
    }
    

    @PostMapping("/add-to-cart")
    public String addProductInCart(@RequestParam String productId, @RequestParam int quantity, HttpSession session) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (product != null) {
            cartService.addProductInCart(product, quantity, session);
        }
        
        return "redirect:/product";
    }
    

    
}
