package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.OrderHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class OrderHistoryRepository {

    private EntityManager em;

    public OrderHistoryRepository(EntityManager em) {
        this.em = em;
    }

    public OrderHistory save(OrderHistory orderHistory) {
        em.persist(orderHistory);
        return orderHistory;
    }

}
