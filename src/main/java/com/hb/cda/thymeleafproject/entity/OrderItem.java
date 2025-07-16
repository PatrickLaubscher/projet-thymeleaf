package com.hb.cda.thymeleafproject.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="order_item")
public class OrderItem {

    @Id
    @UuidGenerator
    private String id;
    private Integer qty;
    
    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;


    
    public OrderItem() {
    }

    public OrderItem(Integer qty, Product product, Order order) {
        this.qty = qty;
        this.product = product;
        this.order = order;
    }


    public OrderItem(String id, Integer qty, Product product, Order order) {
        this.id = id;
        this.qty = qty;
        this.product = product;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }



    public Order getOrder() {
        return order;
    }



    public void setOrder(Order order) {
        this.order = order;
    }

    

    
    
}
