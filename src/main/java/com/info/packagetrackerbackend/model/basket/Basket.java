package com.info.packagetrackerbackend.model.basket;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private SystemUser owner;

    @OneToMany(
            mappedBy = "basket",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

}
