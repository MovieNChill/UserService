package com.movienchill.userservice.lib;

import lombok.Data;

@Data
public class ErrorResponse<T> {
    private String message;
    private String code;
    private T object;

    public ErrorResponse(CustomException e, T object) {
        this.message = e.getMessage();
        this.code = e.getCode();
        this.object = object;
    }
}