package com.movienchill.userservice.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "pseudo is mandatory")
    @Column(name = "pseudo")
    private String pseudo;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "password should be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$")
    @Column(name = "password")
    private String password;
}