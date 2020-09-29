package com.info.packagetrackerbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@NamedQuery(
        name = "OrderHistory.getByOrderId",
        query = "select oh from OrderHistory oh" +
                " join oh.order o on o.id = ?1"
)
public class OrderHistory extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String status;
    private String statusColor;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    public OrderHistory(Order order) {
        this.order = order;
        this.code = order.getCode();
        this.status = order.getStatus();
        this.statusColor = order.getStatusColor();
    }

}
