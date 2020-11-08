package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByOwnerId(Long ownerId, Pageable pageable);

    Page<Product> findByOwnerIdNot(Long ownerId, Pageable pageable);

    @Query("SELECT p.available FROM Product p WHERE p.id=?1")
    Boolean available(Long productId);
}
