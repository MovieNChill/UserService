package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

public class AuthGoogleException extends CustomException {
    public String code;

    public AuthGoogleException() {
        super("Google account is already attached to this email, you must use the Google Auth",
                "invalid_type_connection");
    }
}
