package com.info.packagetrackerbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "person_order")
@NoArgsConstructor
public class PersonOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Order order;

}
