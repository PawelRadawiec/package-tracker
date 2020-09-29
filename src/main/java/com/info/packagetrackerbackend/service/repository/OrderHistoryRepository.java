package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> getByOrderId(@Param("id") Long id);
}
