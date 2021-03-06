package com.info.packagetrackerbackend.model.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String orderType;

    @NotEmpty
    private String transportType;

    @OneToOne(mappedBy = "order")
    @JsonBackReference
    private Product product;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonManagedReference
    private PersonOrder person;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private SystemUser owner;

    @Valid
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
