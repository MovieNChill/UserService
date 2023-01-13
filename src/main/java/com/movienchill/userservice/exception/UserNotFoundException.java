package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class UserNotFoundException extends CustomException {
    public String code;

    public UserNotFoundException() {
        super("We can't find an account with this email or pseudo", "user_not_found");
    }
}
