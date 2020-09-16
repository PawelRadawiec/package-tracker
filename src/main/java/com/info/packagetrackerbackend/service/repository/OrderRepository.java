package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderByIdAndCode(@Param("id") Long id, @Param("code") String code);
}
