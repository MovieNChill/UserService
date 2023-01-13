package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class PasswordInvalidException extends CustomException {
    public String code;

    public PasswordInvalidException() {
        super("The password is not strong enough",
                "invalid_password");
    }
}
