package com.service.productCatalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchProductDTO {
    private String query;
    private Integer pageNumber;
    private Integer pageSize;
}
