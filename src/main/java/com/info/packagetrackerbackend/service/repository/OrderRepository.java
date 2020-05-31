package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class OrderRepository {

    private EntityManager em;

    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public Order save(Order order) {
        em.persist(order);
        return order;
    }

    public Order update(Order order) {
        em.merge(order);
        return order;
    }

}
