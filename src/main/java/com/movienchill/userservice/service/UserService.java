package com.movienchill.userservice.service;

import com.movienchill.userservice.model.User;
import com.movienchill.userservice.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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

    public User login(String login, String password) {
        try {
            User user = userDAO.findByPseudoAndPassword(login, password);
            if (user != null) {
                // TODO Generate Token from Auth Service
                return user;
            }
            user = userDAO.findByEmailAndPassword(login, password);
            if (user != null) {
                // TODO Generate Token from Auth Service
                return user;
            }
            log.info("The user does not exist");
        } catch (Exception e) {
            log.error("Error when logging user : {} ", e.getMessage());
        }
        return null;
    }

    public Boolean register(User user) {
        if (save(user)) {
            return Boolean.TRUE;
        }
        log.error("An error occured during the creation of the user");
        return Boolean.FALSE;
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
        } else {
            log.info("The user does not exist");
        }
        return user;
    }

    private Boolean save(User user) {
        try {
            userDAO.save(user);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Error when saving entity to database : {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
