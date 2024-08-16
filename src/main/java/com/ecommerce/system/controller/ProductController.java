package com.ecommerce.system.controller;

import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.model.Product;
import com.ecommerce.system.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @Operation(summary = "Display all products in home page")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "Display one product info")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping("/product")
    @Operation(summary = "Add new product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
            throws IOException {
        return new ResponseEntity<>(productService.insert(product, imageFile), HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product,
                                           @RequestPart MultipartFile imageFile) throws IOException {
        ProductDto productDto = productService.update(id, product, imageFile);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
         productService.delete(id);
         return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("product/{productId}/image")
    @Operation(summary = "Fetch image to product")
    public ResponseEntity<?> getProductImage(@PathVariable int productId) {
        ProductDto productDto = productService.getProductImage(productId);
            return ResponseEntity.ok().contentType(MediaType.valueOf(productDto.getImageType()))
                    .body(productDto.getImageDate());
    }

    @GetMapping("/products/search")
    @Operation(summary = "Search for product by name, brand, description or category")
    public ResponseEntity<?> getProductSearch(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }
    
}