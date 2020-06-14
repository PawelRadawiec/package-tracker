package com.info.packagetrackerbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@ToString
@Table(name = "package_order")
@NoArgsConstructor
@NamedQuery(
        name = "order.getOrderByIdAndCode",
        query = "select o from Order o where o.id = ?1 and o.code = ?2"
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String status;
    private String statusColor;
    private LocalDate orderStartDate;
    private LocalDate orderEndDate;
    private String orderType;
    private String transportType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonManagedReference
    private PersonOrder person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private OrderAddress address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderHistory> orderHistoryList;

    public Order(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
