package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Optional;

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

    public Optional<Order> getOrderByLongAndCode(Long id, String code) {
        Query query = em.createNamedQuery("order.getOrderByIdAndCode");
        query.setParameter(1, id);
        query.setParameter(2, code);
        return query.getResultList().stream().findFirst();
    }

}
