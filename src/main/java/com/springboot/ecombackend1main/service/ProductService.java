package com.springboot.ecombackend1main.service;

import com.springboot.ecombackend1main.model.Product;
import com.springboot.ecombackend1main.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Cacheable(key = "#root.methodName", value = "findAllProducts")
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Cacheable(key = "#id", value = "findProductById")
    public Product findById(int id) {
        return productRepo.findById(id).orElse(null);
    }

    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public Product insert(Product product,  MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return productRepo.save(product);
    }

    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public Product update(int id, Product product,  MultipartFile imageFile) throws IOException {
        if (productRepo.findById(id).isPresent()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageDate(imageFile.getBytes());
            return productRepo.save(product);
        } else
            return null;
    }

    @CacheEvict(key = "#id", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public void delete(int id) {
        productRepo.deleteById(id);
    }

    @Cacheable(key = "#keyword", value = "searchForProduct")
    public List<Product> searchProduct(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
