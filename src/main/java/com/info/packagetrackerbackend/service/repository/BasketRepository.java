package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
}
