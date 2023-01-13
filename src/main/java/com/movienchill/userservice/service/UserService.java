package com.movienchill.userservice.service;

import com.movienchill.userservice.exception.EmailAlreadyExistsException;
import com.movienchill.userservice.exception.IncorrectPasswordException;
import com.movienchill.userservice.exception.InvalidPasswordException;
import com.movienchill.userservice.exception.PseudoAlreadyExistsException;
import com.movienchill.userservice.exception.UserNotFoundException;
import com.movienchill.userservice.model.User;
import com.movienchill.userservice.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> findAll() {
        try {
            return userDAO.findAll();
        } catch (Exception e) {
            log.error("Error when finding all Users : {}", e.getMessage());
            return null;
        }
    }

    public User findById(Long id) {
        try {
            if (userDAO.findById(id).isPresent()) {
                User user = userDAO.findById(id).get();
                return user;
            } else {
                log.info("The user[{}] doesn't exist", id);
            }
        } catch (Exception e) {
            log.error("Error when finding user[{}] : {} ", id, e.getMessage());
        }
        return null;
    }

    private User findUserToLogin(String login) {
        User user = userDAO.findByPseudo(login);
        if (user != null) {
            return user;
        }
        user = userDAO.findByEmail(login);
        if (user != null) {

            return user;
        }
        return null;
    }

    public User login(String login, String clearPassword) throws IncorrectPasswordException, UserNotFoundException {
        try {
            User user = findUserToLogin(login);
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                // TODO Generate Token from Auth Service
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(clearPassword, user.getPassword())) {
                    return user;
                } else {
                    throw new IncorrectPasswordException();
                }
            }

        } catch (Exception e) {
            log.error("Error when logging user : {} ", e.getMessage());
            throw e;
        }
    }

    public void register(User user)
            throws EmailAlreadyExistsException, PseudoAlreadyExistsException, InvalidPasswordException {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
        if (userDAO.findByPseudo(user.getPseudo()) != null) {
            throw new PseudoAlreadyExistsException();
        }
        if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[ç@#$%^&+=])(?=\\S+$).{4,}$")) {
            throw new InvalidPasswordException();
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }
        this.save(user);
    }

    public Boolean delete(Long id) {
        if (userDAO.findById(id).isPresent()) {
            userDAO.delete(userDAO.findById(id).get());
            return Boolean.TRUE;
        } else {
            log.info("The user id[{}] does not exist", id);
            return Boolean.FALSE;
        }
    }

    public User update(User user) {
        if (userDAO.findById(user.getId()).isPresent()) {
            save(user);
            return user;
        } else {
            log.info("The user does not exist");
        }
        return null;
    }

    private Boolean save(User user) {
        try {
            userDAO.save(user);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Error when saving user : {} ", e.getMessage());
            throw e;
        }
    }
}