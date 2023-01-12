package com.movienchill.userservice.lib;

import lombok.Data;

@Data
public class CustomResponse<T> {
    private String message;
    private String code;
    private T object;

    public CustomResponse(CustomException e, T object) {
        this.object = object;
        if (e == null)
            return;
        this.message = e.getMessage();
        this.code = e.getCode();
    }
}