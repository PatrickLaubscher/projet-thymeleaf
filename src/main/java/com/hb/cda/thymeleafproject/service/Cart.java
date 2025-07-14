package com.hb.cda.thymeleafproject.service;

import java.util.HashMap;
import java.util.Map;

import com.hb.cda.thymeleafproject.entity.Product;

public class Cart {

    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void decreaseProductQuantity(Product product, int quantity) {
        if (items.containsKey(product)) {
            int currentQty = items.get(product);
            int newQty = currentQty - quantity;
            if (newQty > 0) {
                items.put(product, newQty);
            } else {
                items.remove(product);
            }
        }
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    
}
