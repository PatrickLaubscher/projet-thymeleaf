package com.hb.cda.thymeleafproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String displayRegister( @AuthenticationPrincipal UserDetails userDetails, Model model) {

        if(userDetails != null && userDetails.isEnabled()) {
            model.addAttribute("username", userDetails.getUsername());
        }
        
        return "home";
    }
    
}
