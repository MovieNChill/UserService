package com.movienchill.userservice;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String pseudo;
    private String email;
    private String password;
}
