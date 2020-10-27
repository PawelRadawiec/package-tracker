package com.info.packagetrackerbackend.model.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListRequest {
    private String search;
    private String name;
    private String code;
    private String orderType;
    private String transportType;
}
