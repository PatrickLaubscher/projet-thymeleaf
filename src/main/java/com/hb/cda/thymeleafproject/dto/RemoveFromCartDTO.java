package com.hb.cda.thymeleafproject.dto;

import jakarta.validation.constraints.NotNull;

public class RemoveFromCartDTO {

    @NotNull
    private String productId;

    

    public RemoveFromCartDTO() {
    }

    public RemoveFromCartDTO(@NotNull String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    



    
}
