package com.info.packagetrackerbackend.service.repository.specification;

import com.info.packagetrackerbackend.model.order.Order;
import com.info.packagetrackerbackend.model.order.OrderListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OrderListSpecification extends BaseSpecification<Order, OrderListRequest> {

    @Override
    public Specification<Order> getGeneralFilter(OrderListRequest request) {
        return Specification.where(nameContains(request.getSearch()))
                .or(codeContains(request.getSearch()));
    }

    public Specification<Order> getFilter(OrderListRequest request) {
        return Specification
                .where(nameContains(request.getName()))
                .or(codeContains(request.getCode()));
    }

    private Specification<Order> nameContains(String name) {
        return orderAttributesContains("name", name);
    }

    private Specification<Order> codeContains(String code) {
        return orderAttributesContains("code", code);
    }


    private Specification<Order> orderAttributesContains(String attribute, String value) {
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
