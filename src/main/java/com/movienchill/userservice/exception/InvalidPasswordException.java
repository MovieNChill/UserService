package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class InvalidPasswordException extends CustomException {
    public String code;

    public InvalidPasswordException() {
        super("The password is not strong enough",
                "invalid_password");
    }
}
