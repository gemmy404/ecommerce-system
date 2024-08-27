package com.ecommerce.system.service.impl;

import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.mapper.ProductMapper;
import com.ecommerce.system.model.Product;
import com.ecommerce.system.repository.specification.ProductSpec;
import com.ecommerce.system.service.ProductService;
import com.ecommerce.system.repository.ProductRepo;
import com.ecommerce.system.service.util.ProductUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ProductMapper productMapper;

    private final MessageSource messageSource;

    private final ProductUtil productUtil;

    @Override
    @Cacheable(key = "{#pageNum, #size}", value = "findAllProducts")
    public Page<ProductDto> findAll(int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        Page<Product> products = productRepo.findAll(pageable);
        return products.map(productMapper::toProductDto);
    }

    @Override
    @Cacheable(key = "#id", value = "findProductById")
    public ProductDto findById(int id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent())
            return productMapper.toProductDto(product.get());
        else {
            String[] msgParams = {String.valueOf(id)};
            String msg = messageSource.getMessage("exception.recordNotFound.occurred", msgParams,
                    LocaleContextHolder.getLocale());
            throw new RuntimeException(msg);
        }
    }

    @Override
    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public ProductDto insert(ProductDto productDto, MultipartFile imageFile, String pathType) throws IOException {
        if (productDto.getId() != null) {
            String[] msgParams = {productDto.getId().toString()};
            String msg = messageSource.getMessage("exception.duplicateKey", msgParams,
                    LocaleContextHolder.getLocale());
            throw new DuplicateKeyException(msg);
        }
        String imagePath = productUtil.storeImage(imageFile, pathType);
        productDto.setImagePath(imagePath);
        return productMapper.toProductDto
                (productRepo.save(productMapper.toProduct(productDto)));
    }

    @Override
    @CacheEvict(key = "#root.methodName", value = {"findAllProducts", "findProductById"}, allEntries = true)
    public ProductDto update(int id, ProductDto productDto, MultipartFile imageFile, String pathType) throws IOException {
        if (productRepo.findById(id).isEmpty()) {
            String[] msgParams = {String.valueOf(id)};
            String msg = messageSource.getMessage("exception.recordNotFound.occurred", msgParams,
                    LocaleContextHolder.getLocale());
            throw new RuntimeException(msg);
        }
        String imagePath = productUtil.storeImage(imageFile, pathType);
        productDto.setImagePath(imagePath);
        productRepo.save(productMapper.toProduct(productDto));
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
    @Cacheable(key = "{#keyword, #pageNum, #size}", value = "searchForProduct")
    public Page<ProductDto> searchProduct(String keyword, int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        if (keyword == null || keyword.isEmpty() || keyword.isBlank()) {
            return findAll(pageNum, size);
        }
        Specification<Product> products = Specification
                .where(ProductSpec.searchKeyword(keyword));
        return productRepo.findAll(products, pageable)
                .map(productMapper::toProductDto);
    }

    @Override
    @Cacheable(key = "{#categoryId, #pageNum, #size}", value = "getProductByCategoryId")
    public Page<ProductDto> getProductByCategoryId(int categoryId, int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        Specification<Product> products = Specification
                .where(ProductSpec.getProductByCategoryId(categoryId));
        return productRepo.findAll(products, pageable)
                .map(productMapper::toProductDto);
    }

}
