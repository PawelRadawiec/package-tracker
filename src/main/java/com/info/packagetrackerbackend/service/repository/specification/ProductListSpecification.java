package com.info.packagetrackerbackend.service.repository.specification;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.ProductListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductListSpecification extends BaseSpecification<Product, ProductListRequest> {
    @Override
    public Specification<Product> getGeneralFilter(ProductListRequest request) {
        return Specification.where(nameContains(request.getSearch()));
    }

    public Specification<Product> productsOwnerNotIn(ProductListRequest request) {
        return Specification.where(nameContains(request.getName()))
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(
                        root.join("owner").get("id"), request.getOwnerId())
                );
    }

    private Specification<Product> nameContains(String name) {
        return productAttributesContains("name", name);
    }

    private Specification<Product> productAttributesContains(String attribute, String value) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        });
    }

}
