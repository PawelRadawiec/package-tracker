package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Order getOrderByIdAndCode(@Param("id") Long id, @Param("code") String code);
}
