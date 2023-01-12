package com.movienchill.userservice.exception;

import com.movienchill.userservice.lib.CustomException;

import lombok.Data;

public class PseudoAlreadyExistsException extends CustomException {
    public String code;

    public PseudoAlreadyExistsException() {
        super("The pseudo is already in use.", "pseudo_already_exists");
    }
}