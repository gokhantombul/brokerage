package com.inghubstr.brokerage.v1.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiException {

    private final String message;
    //private final Throwable throwable;
    private final HttpStatus status;
    private final String timestamp;
    private String field;

    public ApiException(String message, HttpStatus status, String timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiException(String message, String field, HttpStatus status, String timestamp) {
        this.message = message;
        this.field = field;
        this.status = status;
        this.timestamp = timestamp;
    }

}
