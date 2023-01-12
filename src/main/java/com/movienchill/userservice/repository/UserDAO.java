package com.movienchill.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movienchill.userservice.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    User findByPseudoAndPassword(String pseudo, String password);

    User findByEmailAndPassword(String email, String password);

    User findByPseudo(String pseudo);

    User findByEmail(String email);
}