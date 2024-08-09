package com.ecommerce.system.controller;

import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.mapper.ProductMapper;
import com.ecommerce.system.model.Product;
import com.ecommerce.system.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/products")
    @Operation(summary = "Display all products in home page")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = productMapper.map(product);
            productDtos.add(productDto);
        }
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "Display one product info")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        Product product = productService.findById(id);
        if (product != null) {
            ProductDto productDto = productMapper.map(product);
            return ResponseEntity.ok(productDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    @Operation(summary = "Add new product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
//            Product product = productMapper.unMap(productDto);
            Product product1 = productService.insert(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product,
                                           @RequestPart MultipartFile imageFile) {
//        Product product = productMapper.unMap(productDto);
        Product product1 = null;
        try {
            product1 = productService.update(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if (product1 != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        Product product = productService.findById(id);
        if (product != null) {
            productService.delete(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("product/{productId}/image")
    @Operation(summary = "Fetch image to product")
    public ResponseEntity<?> getProductImage(@PathVariable int productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            ProductDto productDto = productMapper.map(product);
            byte[] imageFile = productDto.getImageDate();
            return ResponseEntity.ok().
                    contentType(MediaType.valueOf(productDto.getImageType()))
                    .body(imageFile);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    @Operation(summary = "Search for product by name, brand, description or category")
    public ResponseEntity<?> getProductSearch(@RequestParam String keyword) {
        List<Product> products = productService.searchProduct(keyword);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(productMapper.map(product));
        return ResponseEntity.ok(productDtos);
    }
}
