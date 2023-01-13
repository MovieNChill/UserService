package com.movienchill.userservice.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movienchill.userservice.constant.Router;
import com.movienchill.userservice.lib.CustomException;
import com.movienchill.userservice.lib.CustomResponse;
import com.movienchill.userservice.lib.LoginDTO;
import com.movienchill.userservice.model.User;
import com.movienchill.userservice.service.UserService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Router.URL_USERS)
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error when retrieving all users : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserByid(@PathVariable("id") Long id) {
        User userDto = null;
        try {
            User user = userService.findById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error when retrieving user[{}]", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.update(user);
            if (updatedUser != null)
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        try {
            Boolean deleted = userService.delete(id);
            if (deleted)
                return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(deleted, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("An error occurred when deleting the user : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Router.REGISTER)
    public ResponseEntity<CustomResponse<User>> register(@RequestBody User user) {
        try {
            userService.register(user);
            return new ResponseEntity<>(new CustomResponse<>(null, user), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(new CustomResponse<>(e, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Router.LOGIN)
    public ResponseEntity<CustomResponse<User>> login(@RequestBody LoginDTO login) {
        try {
            log.info(login.getLogin());
            User user = userService.login(login.getLogin(), login.getPassword());
            if (user != null)
                return new ResponseEntity<>(new CustomResponse<>(null, user), HttpStatus.OK);
            else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (CustomException e) {
            return new ResponseEntity<>(new CustomResponse<>(e, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error when login user : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
