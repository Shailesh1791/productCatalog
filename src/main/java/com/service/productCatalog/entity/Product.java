package com.service.productCatalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity(name = "Product_Table")
@NamedStoredProcedureQuery(
        name = "Product.countByCategory",
        procedureName = "count_product_byCategory",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "categoryName", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "productCount", type = Integer.class)
        })
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    private String name;
    private String description;
    private String category;
    private double price;

}
