package com.example.spring.api.controller;

import com.example.spring.api.entity.LoginModel;
import com.example.spring.api.entity.User;
import com.example.spring.api.entity.UserModel;
import com.example.spring.api.sevice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
     private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel model){
      //  System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
//        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getEmail(),model.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<String>("User logged in", HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserModel userModel){
        return userService.createUser(userModel);
    }
}
