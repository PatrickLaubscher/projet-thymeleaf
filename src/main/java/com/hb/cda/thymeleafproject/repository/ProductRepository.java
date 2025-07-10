package com.hb.cda.thymeleafproject.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hb.cda.thymeleafproject.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    Optional<Product> findByName(String name);
}
