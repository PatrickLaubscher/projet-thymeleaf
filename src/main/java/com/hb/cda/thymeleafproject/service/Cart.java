package com.hb.cda.thymeleafproject.service;

import java.util.HashMap;
import java.util.Map;

import com.hb.cda.thymeleafproject.entity.Product;

public class Cart {

    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 1) + quantity);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    
}
