package com.info.packagetrackerbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_address")
@NoArgsConstructor
public class OrderAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street;
    private String city;
    private String postalCode;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonBackReference
    private Order order;

}
