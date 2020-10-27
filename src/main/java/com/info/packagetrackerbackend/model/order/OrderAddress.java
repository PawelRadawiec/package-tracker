package com.info.packagetrackerbackend.model.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "order_address")
@NoArgsConstructor
public class OrderAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String street;

    @NotEmpty
    private String city;

    @NotEmpty
    private String postalCode;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonBackReference
    private Order order;

}
