package com.service.productCatalog.utils;

import com.service.productCatalog.commons.RestApiStatus;
import com.service.productCatalog.constants.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class RequestUtils {

    private RequestUtils() {

    }

    public static RestApiStatus prepareApiStatus(HttpStatus status, ApiStatus apiStatus,
                                                 boolean isExceptionTrace, Exception exception, WebRequest webRequest) {
        return RestApiStatus.builder()
                .requestId(webRequest.getSessionId())
                .requestTime(LocalDateTime.now())
                .requestUrl(webRequest.getContextPath())
                .exceptionTrace(isExceptionTrace ? "Yes" : "No")
                .httpStatusCode(status.value())
                .httpStatusDesc(status.getReasonPhrase())
                .subStatusCode(apiStatus.getStatusCode())
                .subStatusDesc(apiStatus.getStatusDesc())
                .build();
    }
}
