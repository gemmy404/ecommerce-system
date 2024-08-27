package com.ecommerce.system.mapper;

import com.ecommerce.system.dto.CategoryDto;
import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.model.Category;
import com.ecommerce.system.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "category.id" , target = "categoryId")
    ProductDto toProductDto(Product product);

    @Mapping(target = "category.id" , source = "categoryId")
    Product toProduct(ProductDto productDto);

    @Mapping(source = "products", target = "products")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "products", source = "products")
    Category toCategory(CategoryDto categoryDto);

}
