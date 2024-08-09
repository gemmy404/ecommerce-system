package com.ecommerce.system.service.impl;

import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.mapper.ProductMapper;
import com.ecommerce.system.model.Product;
import com.ecommerce.system.repository.ProductSpec;
import com.ecommerce.system.service.ProductService;
import com.ecommerce.system.repository.ProductRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Cacheable(key = "#root.methodName", value = "findAllProducts")
    public List<ProductDto> findAll() {
        List<Product> products = productRepo.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = productMapper.map(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    @Cacheable(key = "#id", value = "findProductById")
    public ProductDto findById(int id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent())
            return productMapper.map(product.get());
        else
            throw new RuntimeException("Product Not Found");
    }

    @Override
    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public ProductDto insert(Product product, MultipartFile imageFile) {
//        Product product = productMapper.unMap(productDto);
        try {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageDate(imageFile.getBytes());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return productMapper.map(productRepo.save(product));
    }

    @Override
    @Cacheable(key = "#productId", value = "getProductImage")
    public ProductDto getProductImage(int productId) {
        Product product = productRepo.findById(productId).get();
        return productMapper.map(product);
    }

    @Override
    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public ProductDto update(int id, Product product, MultipartFile imageFile) throws IOException {
        try {
            if (productRepo.findById(id).isPresent()) {
                product.setImageName(imageFile.getOriginalFilename());
                product.setImageType(imageFile.getContentType());
                product.setImageDate(imageFile.getBytes());
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return productMapper.map(productRepo.save(product));
    }

    @Override
    @CacheEvict(key = "#id", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public void delete(int id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent())
            productRepo.deleteById(id);
    }

    @Override
    @Cacheable(key = "#keyword", value = "searchForProduct")
    public List<Product> searchProduct(String keyword) {
        ProductSpec productSpec = new ProductSpec(keyword);
        return productRepo.findAll(productSpec);
    }

}
