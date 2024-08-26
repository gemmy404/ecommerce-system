package com.ecommerce.system.controller;

import com.ecommerce.system.dto.ProductDto;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Display all products in home page")
    public ResponseEntity<?> getAllProducts(@RequestParam int pageNum, @RequestParam int size) {
        return ResponseEntity.ok(productService.findAll(pageNum, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Display one product info")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Add new product")
    public ResponseEntity<?> addProduct(@RequestPart ProductDto product, @RequestPart MultipartFile imageFile)
            throws IOException {
        return new ResponseEntity<>(productService.insert(product, imageFile),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart ProductDto product,
                                           @RequestPart MultipartFile imageFile) throws IOException {
        return new ResponseEntity<>(productService.update(id, product, imageFile),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/{productId}/image")
    @Operation(summary = "Fetch image to product")
    public ResponseEntity<?> getProductImage(@PathVariable int productId) {
        ProductDto productDto = productService.getProductImage(productId);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productDto.getImageType()))
                .body(productDto.getImageData());
    }

    @GetMapping("/search")
    @Operation(summary = "Search for product by name, brand, description or category")
    public ResponseEntity<?> getProductSearch(@RequestParam String keyword, @RequestParam int pageNum,
                                              @RequestParam int size) {
        return ResponseEntity.ok(productService.searchProduct(keyword, pageNum, size));
    }

    @GetMapping("/category")
    @Operation(summary = "Get products by category id")
    public ResponseEntity<?> getProductByCategoryId(@RequestParam int categoryId, @RequestParam int pageNum,
                                                    @RequestParam int size) {
        return ResponseEntity.ok(productService.getProductByCategoryId(categoryId, pageNum, size));
    }

}