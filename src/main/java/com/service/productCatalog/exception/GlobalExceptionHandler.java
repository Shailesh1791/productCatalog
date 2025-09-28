package com.service.productCatalog.exception;


import com.service.productCatalog.commons.RestApiResponse;
import com.service.productCatalog.commons.RestApiStatus;
import com.service.productCatalog.constants.ApiStatus;
import com.service.productCatalog.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<RestApiResponse<RestApiStatus, Void>> handleCustomNotFoundException(CustomNotFoundException e, WebRequest request) {
        log.error("handle Custom Not Found Exception", e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(String.valueOf(HttpStatus.NOT_FOUND));
        errorResponse.setMessage(e.getMessage());
        RestApiResponse<RestApiStatus, Void> restApiResponse = new RestApiResponse<>();
        restApiResponse.setStatus(RequestUtils.prepareApiStatus(HttpStatus.NOT_FOUND, ApiStatus.NOT_FOUND,
                true, e, request));
        return new ResponseEntity<>(restApiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiResponse<RestApiStatus, Void>> handleException(Exception e, WebRequest request) {
        log.error("handleException", e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        errorResponse.setMessage(e.getMessage());
        RestApiResponse<RestApiStatus, Void> restApiResponse = new RestApiResponse<>();
        restApiResponse.setStatus(RequestUtils.prepareApiStatus(HttpStatus.INTERNAL_SERVER_ERROR, ApiStatus.INTERNAL_SERVER_ERROR,
                true, e, request));
        return new ResponseEntity<>(restApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiResponse<RestApiStatus, Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        log.error("handle Method Argument Not Valid Exception", e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(String.valueOf(HttpStatus.BAD_REQUEST));
        errorResponse.setMessage(e.getMessage());
        RestApiResponse<RestApiStatus, Void> restApiResponse = new RestApiResponse<>();
        restApiResponse.setStatus(RequestUtils.prepareApiStatus(HttpStatus.BAD_REQUEST, ApiStatus.INVALID_PARAMETER,
                true, e, request));
        return new ResponseEntity<>(restApiResponse, HttpStatus.BAD_REQUEST);
    }
}
