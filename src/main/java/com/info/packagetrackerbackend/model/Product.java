package com.info.packagetrackerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.model.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String code;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private SystemUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @OneToOne(mappedBy = "product")
    private Order order;

}
