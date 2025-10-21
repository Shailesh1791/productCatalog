package com.service.productCatalog.constant;

public enum ApiStatus {

    API_SUCCESSFULLY_EXECUTED(200,"OK"),

    RESOURCE_NOT_FOUND(404,"Requested resource not found"),

    RESOURCE_CREATED(201,"Created"),

    INVALID_PAYLOAD(400,"Invalid request payload"),

    METHOD_NOT_ALLOWED(405,"Method not allowed on the object"),

    INTERNAL_SERVER_ERROR(500,"Internal Server Error"),

    INVALID_PARAMETER(400,"Bad Request"),

    INVALID_REQUEST(400,"Bad Request"),

    UNAUTHORIZED(401,"Unauthorized"),

    FORBIDDEN(403,"Forbidden"),

    NOT_FOUND(404,"Not Found"),

    CONFLICT(409,"Conflict"),

    MISSING_REQUIRED_HEADER(400,"Missing Required header");

    private final int statusCode;

    private final String statusDesc;

    ApiStatus(int statusCode, String statusDesc){
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}
