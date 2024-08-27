package com.ecommerce.system.repository;

import com.ecommerce.system.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Page<Category> findByNameIgnoreCase(String categoryName, Pageable pageable);

}
