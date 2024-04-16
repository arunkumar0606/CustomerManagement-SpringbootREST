package com.example.spring.api.controller;

import com.example.spring.api.entity.LoginModel;
import com.example.spring.api.entity.User;
import com.example.spring.api.entity.UserModel;
import com.example.spring.api.sevice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
     private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel model,HttpServletRequest req){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(model.getEmail(),model.getPassword());
       try {
           Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
           SecurityContext context = SecurityContextHolder.getContext();
           context.setAuthentication(authentication);
           HttpSession session = req.getSession(true);
           session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
         //System.out.println(context.getAuthentication().toString());
       }
       catch (Exception e){
           System.out.println("Error Authenticating User " + e);
       }
        return new ResponseEntity<String>("User logged in", HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserModel userModel){
        return userService.createUser(userModel);
    }

    @GetMapping("/currentUser")
    @ResponseStatus(HttpStatus.OK)
    public String currentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return "Current Logged User : "+context.getAuthentication().getName();
    }
}
