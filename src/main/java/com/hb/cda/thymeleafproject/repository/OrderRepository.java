package com.hb.cda.thymeleafproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.cda.thymeleafproject.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
    
}
