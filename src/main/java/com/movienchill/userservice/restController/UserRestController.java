package com.movienchill.userservice.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movienchill.userservice.constant.Router;
import com.movienchill.userservice.model.User;
import com.movienchill.userservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Router.URL_USERS)
public class UserRestController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "", nickname = "getAllUsers")
    @GetMapping()
    public List<User> getAllUsers() {
        try {
            return userService.findAll();
        } catch (Exception e) {
            log.error("Error when retrieving all users : {}", e.getMessage());
            return null;
        }
    }

    @ApiOperation(value = "", nickname = "getUserByid")
    @GetMapping(value = "/{id}")
    public User getUserByid(@PathVariable("id") Long id) {
        User userDto = null;
        try {
            return userService.findById(id);
        } catch (Exception e) {
            log.error("Error when retrieving user[{}]", e.getMessage());
            return null;
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user) {
        try {
            return userService.update(user);
        } catch (Exception e) {
            log.error("Error when login user : {}", e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        try {
            return userService.delete(id);
        } catch (Exception e) {
            log.error("An error occurred when deleting the user : {}", e.getMessage());
            return Boolean.FALSE;
        }
    }

    @ApiOperation(value = "", nickname = "register")
    @PostMapping(Router.REGISTER)
    public User register(@RequestBody User user) {
        try {
            if (userService.register(user)) {
                return user;
            }
        } catch (Exception e) {
            log.error("Error when registered the user : {}", e.getMessage());
        }

        return null;
    }

    @ApiOperation(value = "", nickname = "login")
    @PutMapping(Router.LOGIN)
    public User login(@RequestBody User user) {
        try {
            return userService.login(user.getPseudo(), user.getPassword());
        } catch (Exception e) {
            log.error("Error when login user : {}", e.getMessage());
            return null;
        }
    }
}
