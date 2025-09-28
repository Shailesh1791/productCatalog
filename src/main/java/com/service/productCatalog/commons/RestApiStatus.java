package com.service.productCatalog.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestApiStatus implements Serializable {

    private String requestId;

    private Integer httpStatusCode;

    private String httpStatusDesc;

    private Integer subStatusCode;

    private String subStatusDesc;

    private String exceptionTrace;

    private String requestUrl;

    private LocalDateTime requestTime;
}
