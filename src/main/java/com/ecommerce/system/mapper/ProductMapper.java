package com.ecommerce.system.mapper;

import com.ecommerce.system.model.Product;
import com.ecommerce.system.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id" , target = "categoryId")
    ProductDto toProductDto(Product product);

    @Mapping(target = "category.id" , source = "categoryId")
    Product toProduct(ProductDto productDto);

}
