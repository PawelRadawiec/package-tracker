package com.info.packagetrackerbackend.service.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T, U> {
    public abstract Specification<T> getGeneralFilter(U request);

    protected String containsLowerCase(String searchField) {
        String wildcard = "%";
        return wildcard + searchField.toLowerCase() + wildcard;
    }

}
