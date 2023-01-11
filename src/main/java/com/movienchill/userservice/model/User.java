package com.movienchill.userservice.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "pseudo", "email" }) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "pseudo is mandatory")
    @Column(name = "pseudo", unique = true)
    private String pseudo;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "password should be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[ç@#$%^&+=])(?=\\S+$).{4,}$", message = "password should contain at least one digit, one lower case, one upper case, one special character and no whitespace")
    @Column(name = "password")
    private String password;
}