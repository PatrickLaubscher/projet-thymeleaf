package com.hb.cda.thymeleafproject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="customer_order")
public class Order {

    @Id
    @UuidGenerator
    private String id;
    private LocalDateTime orderDate;
    private Double totalPrice;
    
    @ManyToOne
    private User customer;

    @OneToMany(mappedBy="order", orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public Order() {
    }

    public Order(LocalDateTime orderDate, Double totalPrice, User customer) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public Order(String id, LocalDateTime orderDate, Double totalPrice, User customer, List<OrderItem> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.orderItems = orderItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    
    
}
