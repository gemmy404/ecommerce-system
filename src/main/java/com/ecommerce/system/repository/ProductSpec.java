package com.ecommerce.system.repository;

import com.ecommerce.system.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class ProductSpec implements Specification<Product> {

    private String keyword;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%" + keyword.toLowerCase() + "%");
        Predicate descriptionPredicate =  criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                "%" + keyword.toLowerCase() + "%");
        Predicate brandPredicate =  criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")),
                "%" + keyword.toLowerCase() + "%");
        Predicate categoryPredicate =  criteriaBuilder.like(criteriaBuilder.lower(root.get("category")),
                "%" + keyword.toLowerCase() + "%");
        return criteriaBuilder.or(namePredicate, descriptionPredicate, brandPredicate, categoryPredicate);
    }
}
