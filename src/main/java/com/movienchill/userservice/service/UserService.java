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
            throw e;
        }
        return null;
    }

    public void register(User user) {
        save(user);
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
            log.error("Error when saving entity to database : {}", e.getMessage());
            throw e;
        }
    }
}
