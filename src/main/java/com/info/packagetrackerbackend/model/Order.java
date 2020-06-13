package com.info.packagetrackerbackend.model;

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
    private PersonOrder person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private OrderAddress address;

    @OneToMany(mappedBy = "order")
    private List<OrderHistory> orderHistoryList;

    public Order(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
