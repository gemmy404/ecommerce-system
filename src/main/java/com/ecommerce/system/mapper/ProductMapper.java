package com.ecommerce.system.mapper;

import com.ecommerce.system.model.Product;
import com.ecommerce.system.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDto map(Product product);

    Product unMap(ProductDto productDto);

}
