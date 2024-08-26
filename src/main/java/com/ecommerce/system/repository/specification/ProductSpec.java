package com.ecommerce.system.repository.specification;

import com.ecommerce.system.model.Category;
import com.ecommerce.system.model.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpec {

    public static Specification<Product> searchKeyword(String keyword) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                -> {
            String searchKeyword = "%" + keyword.toLowerCase() + "%";
            Join<Product, Category> categoryJoin = root.join("category");

            Predicate namePredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("name")), searchKeyword);
            Predicate descriptionPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("description")), searchKeyword);
            Predicate brandPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("brand")), searchKeyword);
            Predicate categoryPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(categoryJoin.get("name")), searchKeyword);

            return criteriaBuilder.or(namePredicate, descriptionPredicate, brandPredicate, categoryPredicate);
        };
    }

    public static Specification<Product> getProductByCategoryId(int categoryId) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }
}
