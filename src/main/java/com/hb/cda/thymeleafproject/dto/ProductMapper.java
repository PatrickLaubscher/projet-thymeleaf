package com.hb.cda.thymeleafproject.dto;

import org.mapstruct.Mapper;

import com.hb.cda.thymeleafproject.entity.Product;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO convertToDTO(Product product);

}
