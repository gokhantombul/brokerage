package com.inghubstr.brokerage.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException apiRequestException) {
        HttpStatus badRequest = Objects.isNull(apiRequestException.getHttpStatus()) ? HttpStatus.BAD_REQUEST : apiRequestException.getHttpStatus();
        ApiException apiException = new ApiException(
                apiRequestException.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        List<ApiException> errors = new ArrayList<>();
        //if (Objects.nonNull(exception.getBindingResult().getFieldErrors())) {
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ApiException apiException = new ApiException(
                            error.getDefaultMessage(),
                            error.getField(),
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    errors.add(apiException);
                });
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errors);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleRuntimeException(Exception exception) {
        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
