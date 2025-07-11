package com.hb.cda.thymeleafproject.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;




@Controller
@RequestMapping("/products")
public class ProductController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ProductController(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @GetMapping("")
    public String displayProductList(Model model, @RequestParam(defaultValue = "1") int pageNb) {

        Pageable firstPageWithTwoElements = PageRequest.of(pageNb-1, 5, Sort.by("name").descending());

        Page<Product> page = productRepository.findAll(firstPageWithTwoElements);
        List<Product> productsList = page.getContent();

        model.addAttribute("pageNb", pageNb);
        model.addAttribute("products", productsList);

        return "products-list";
    }

    

    
}
