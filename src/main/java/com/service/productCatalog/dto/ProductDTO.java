package com.service.productCatalog.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    private long productId;
    private String name;
    private String description;
    private String category;
    private double price;
    private List<ActionType> actions;
}
