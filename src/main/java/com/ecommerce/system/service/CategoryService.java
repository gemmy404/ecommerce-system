package com.ecommerce.system.service;

import com.ecommerce.system.dto.CategoryDto;
import org.springframework.data.domain.Page;


public interface CategoryService {

    Page<CategoryDto> findAll(int pageNum, int size);

    CategoryDto findById(int id);

    CategoryDto insert(CategoryDto categoryDto);

    CategoryDto update(int id, CategoryDto categoryDto);

    void deleteById(int id);

    Page<CategoryDto> findByName(String name, int pageNum, int size);

}
