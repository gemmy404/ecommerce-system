package com.ecommerce.system.controller;

import com.ecommerce.system.dto.CategoryDto;
import com.ecommerce.system.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "Operations related to category management")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Display page of categories with list of products")
    public ResponseEntity<?> getAllCategories(@RequestParam int pageNum, @RequestParam int size) {
        return ResponseEntity.ok(categoryService.findAll(pageNum, size));
    }

    @GetMapping("/{category-id}")
    @Operation(summary = "Display list of products for a specific category-id")
    public ResponseEntity<?> getCategory(@PathVariable("category-id") int id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/name")
    @Operation(summary = "Display list of products for a specific category name")
    public ResponseEntity<?> getCategoryByName(@RequestParam String categoryName, @RequestParam int pageNum,
                                               @RequestParam int size) {
        return ResponseEntity.ok(categoryService.findByName(categoryName, pageNum, size));
    }

    @PostMapping()
    @Operation(summary = "Add new category with product")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.insert(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{category-id}")
    @Operation(summary = "update category")
    public ResponseEntity<?> updateCategory(@PathVariable("category-id") int id,
                                            @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.update(id, categoryDto));
    }

    @DeleteMapping("/{category-id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<?> deleteCategory(@PathVariable("category-id") int id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }

}
