package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class IncorrectPasswordException extends CustomException {
    public String code;

    public IncorrectPasswordException() {
        super("Incorrect password",
                "incorrect_password");
    }
}
