package com.ecommerce.system.service.impl;

import com.ecommerce.system.dto.CategoryDto;
import com.ecommerce.system.mapper.CategoryMapper;
import com.ecommerce.system.model.Category;
import com.ecommerce.system.repository.CategoryRepo;
import com.ecommerce.system.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    private final CategoryMapper categoryMapper;

    private final MessageSource messageSource;

    @Override
    @Cacheable(key = "{#pageNum, #size}", value = "findAllCategories")
    public Page<CategoryDto> findAll(int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        return categoryRepo.findAll(pageable)
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    @Cacheable(key = "#id", value = "findCategoryById")
    public CategoryDto findById(int id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent())
            return categoryMapper.toCategoryDto(category.get());
        else {
            String[] msgParams = {String.valueOf(id)};
            String msg = messageSource.getMessage("exception.recordNotFound.occurred", msgParams,
                    LocaleContextHolder.getLocale());
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    @CacheEvict(key = "#root.methodName",
            value = {"findAllCategories", "findCategoryById", "findCategoyByName"}, allEntries = true)
    public CategoryDto insert(CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            String[] msgParams = {categoryDto.getId().toString()};
            String msg = messageSource.getMessage("exception.duplicateKey", msgParams,
                    LocaleContextHolder.getLocale());
            throw new DuplicateKeyException(msg);
        }
        Category category = categoryRepo.save(categoryMapper.toCategory(categoryDto));
        return categoryMapper.toCategoryDto(category);
    }


    @Override
    @CacheEvict(key = "{#root.methodName, #id}",
            value = {"findAllCategories", "findCategoryById", "findCategoyByName"}, allEntries = true)
    public CategoryDto update(int id, CategoryDto categoryDto) {
        findById(id);
        categoryDto.setId(id);
        Category category = categoryRepo.save(categoryMapper.toCategory(categoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    @CacheEvict(key = "#id",
            value = {"findAllCategories", "findCategoryById", "findCategoyByName"}, allEntries = true)
    public void deleteById(int id) {
        if (findById(id) != null) {
            categoryRepo.deleteById(id);
        }
    }

    @Override
    @Cacheable(key = "{#name}", value = "findCategoyByName")
    public Page<CategoryDto> findByName(String name, int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        if (name == null || name.isEmpty() || name.isBlank()) {
            return findAll(pageNum, size);
        }
        return categoryRepo.findByNameIgnoreCase(name, pageable)
                .map(categoryMapper::toCategoryDto);
    }
}
