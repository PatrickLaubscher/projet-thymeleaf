package com.hb.cda.thymeleafproject.service;

import java.util.HashMap;
import java.util.Map;

import com.hb.cda.thymeleafproject.entity.Product;

public class Cart {

    private final Map<String, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        items.put(product.getId(), items.getOrDefault(product.getId(), 0) + quantity);
    }

    public void removeProduct(Product product) {
        items.remove(product.getId());
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void decreaseProductQuantity(Product product, int quantity) {

        if (items.containsKey(product.getId())) {
            int currentQty = items.get(product.getId());
            int newQty = currentQty - quantity;
            if (newQty > 0) {
                items.put(product.getId(), newQty);
            } else {
                items.remove(product.getId());
            }
        }

    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    
}
