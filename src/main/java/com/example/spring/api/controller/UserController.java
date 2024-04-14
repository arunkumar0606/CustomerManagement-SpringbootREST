package com.example.spring.api.controller;

import com.example.spring.api.entity.User;
import com.example.spring.api.entity.UserModel;
import com.example.spring.api.sevice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public User createUser(@RequestBody @Valid UserModel userModel){
//       return userService.createUser(userModel);
//    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable  long id){
        return userService.getUser(id);
    }

    @PutMapping("/updateUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable int id,@RequestBody UserModel userModel){
        return userService.updateUser(id,userModel);
    }

    @DeleteMapping("/deleteUserById/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id){
         userService.deleteUserById(id);
    }
}
