package com.service.productCatalog.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {

    @NotNull(message = "orderId cannot be null")
    @Positive(message = "orderId must be positive")
    private long orderId;

    @NotBlank(message = "Order Name is mandatory")
    private String orderName;

    @NotBlank(message = "description is mandatory")
    private String description;

    @Min(value = 1, message = "price must be at least 1")
    @Max(value = 100000, message = "price must be less than or equal to 100000")
    private long price;

    private List<ActionType> actions;
}
