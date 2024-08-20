package com.ecommerce.system.service;

import com.ecommerce.system.dto.ProductDto;
import com.ecommerce.system.model.Product;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findById(int id);

    ProductDto insert(ProductDto productDto, MultipartFile imageFile) throws IOException;

    ProductDto getProductImage(int productId);

    ProductDto update(int id, ProductDto productDto, MultipartFile imageFile) throws IOException;

    void delete(int id);

    List<ProductDto> searchProduct(String keyword);

}