package com.ecommerce.system.service;

import com.ecommerce.system.model.Product;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    Product insert(Product product, MultipartFile imageFile) throws IOException;

    Product update(int id, Product product, MultipartFile imageFile) throws IOException;

    void delete(int id);

    List<Product> searchProduct(String keyword);

}
