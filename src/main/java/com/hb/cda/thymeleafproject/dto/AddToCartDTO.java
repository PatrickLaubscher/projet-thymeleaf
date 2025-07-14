package com.hb.cda.thymeleafproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToCartDTO {

    @NotNull
    private String productId;
    @Min(1)
    private int quantity;



    public AddToCartDTO(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddToCartDTO() {
    }
    
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
