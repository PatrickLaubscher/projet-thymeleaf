package com.hb.cda.thymeleafproject.dto;

public class LoginFormDTO {

    private String username;
    private String password;

    public LoginFormDTO(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    
}
