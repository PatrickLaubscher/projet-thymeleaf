package com.hb.cda.thymeleafproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.cda.thymeleafproject.entity.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem, String> {
    
}
