package com.ecommerce.system.service.impl;

import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.mapper.ProductMapper;
import com.ecommerce.system.model.Product;
import com.ecommerce.system.repository.ProductSpec;
import com.ecommerce.system.service.ProductService;
import com.ecommerce.system.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ProductMapper productMapper;

    private final MessageSource messageSource;

    @Override
    @Cacheable(key = "#root.methodName", value = "findAllProducts")
    public List<ProductDto> findAll() {
        return productRepo.findAll().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key = "#id", value = "findProductById")
    public ProductDto findById(int id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent())
            return productMapper.map(product.get());
        else {
            String[] msgParams = {String.valueOf(id)};
            String msg = messageSource.getMessage("exception.recordNotFound.occurred", msgParams,
                    LocaleContextHolder.getLocale());
            throw new RuntimeException(msg);
        }
    }

    @Override
    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public ProductDto insert(ProductDto productDto, MultipartFile imageFile) {
        try {
            productDto.setImageName(imageFile.getOriginalFilename());
            productDto.setImageType(imageFile.getContentType());
            productDto.setImageDate(imageFile.getBytes());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        productRepo.save(productMapper.unMap(productDto));
        return productDto;
    }

    @Override
    @Cacheable(key = "#productId", value = "getProductImage")
    public ProductDto getProductImage(int productId) {
        Product product = productRepo.findById(productId).get();
        return productMapper.map(product);
    }

    @Override
    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public ProductDto update(int id, ProductDto productDto, MultipartFile imageFile) throws IOException {
        try {
            if (productRepo.findById(id).isPresent()) {
                productDto.setImageName(imageFile.getOriginalFilename());
                productDto.setImageType(imageFile.getContentType());
                productDto.setImageDate(imageFile.getBytes());
            } else {
                String[] msgParams = {String.valueOf(id)};
                String msg = messageSource.getMessage("exception.recordNotFound.occurred", msgParams,
                        LocaleContextHolder.getLocale());
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        productRepo.save(productMapper.unMap(productDto));
        return productDto;
    }

    @Override
    @CacheEvict(key = "#id", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public void delete(int id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent())
            productRepo.deleteById(id);
        else {
            String[] msgParams = {String.valueOf(id)};
            String msg = messageSource.getMessage("exception.recordNotFound.occurred", msgParams,
                    LocaleContextHolder.getLocale());
            throw new RuntimeException(msg);
        }
    }

    @Override
    @Cacheable(key = "#keyword", value = "searchForProduct")
    public List<ProductDto> searchProduct(String keyword) {
        ProductSpec productSpec = new ProductSpec(keyword);
        return productRepo.findAll(productSpec).stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

}
