package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByOwnerId(Long ownerId, Pageable pageable);

    Page<Product> findByOwnerIdNot(Long ownerId, Pageable pageable);

    @Query("SELECT p.inBasket FROM Product p WHERE p.id=?1")
    Boolean inBasket(Long productId);
}
