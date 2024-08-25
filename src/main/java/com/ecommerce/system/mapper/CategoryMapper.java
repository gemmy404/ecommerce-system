package com.ecommerce.system.mapper;

import com.ecommerce.system.dto.CategoryDto;
import com.ecommerce.system.model.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);

}
