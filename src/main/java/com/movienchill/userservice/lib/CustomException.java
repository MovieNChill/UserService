package com.movienchill.userservice.lib;

import lombok.Data;

@Data
public class CustomException extends Exception {
    public String code;

    public CustomException(String message, String code) {
        super(message);
        this.code = code;
    }
}