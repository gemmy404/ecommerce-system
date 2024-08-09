package com.springboot.ecombackend1main.mapper;

import com.springboot.ecombackend1main.dto.ProductDto;
import com.springboot.ecombackend1main.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDto map(Product product);

    Product unMap(ProductDto productDto);

}
