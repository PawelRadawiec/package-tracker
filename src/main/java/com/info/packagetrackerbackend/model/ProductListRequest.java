package com.info.packagetrackerbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListRequest {
    private String search;
    private String name;
    private Long ownerId;
}
