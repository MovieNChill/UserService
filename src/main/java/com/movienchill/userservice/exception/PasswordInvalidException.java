package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class PasswordInvalidException extends CustomException {
    public String code;

    public PasswordInvalidException() {
        super("The password should contain at least one digit, one lower case, one upper case, one special character and no whitespace",
                "invalid_password");
    }
}
