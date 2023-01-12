package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class EmailAlreadyExistsException extends CustomException {
    public String code;

    public EmailAlreadyExistsException() {
        super("The email address is already in use.", "email_already_exists");
    }
}
