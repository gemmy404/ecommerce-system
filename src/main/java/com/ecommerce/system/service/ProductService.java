package com.ecommerce.system.service;

import com.ecommerce.system.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<ProductDto> findAll(int pageNum, int size);

    ProductDto findById(int id);

    ProductDto insert(ProductDto productDto, MultipartFile imageFile, String pathType) throws IOException;

    ProductDto update(int id, ProductDto productDto, MultipartFile imageFile, String pathType) throws IOException;

    void delete(int id);

    Page<ProductDto> searchProduct(String keyword, int pageNum, int size);

    Page<ProductDto> getProductByCategoryId(int categoryId, int pageNum, int size);

}