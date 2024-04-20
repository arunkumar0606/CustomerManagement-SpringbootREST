package com.example.spring.api.controller;

import com.example.spring.api.entity.JwtResponse;
import com.example.spring.api.entity.LoginModel;
import com.example.spring.api.entity.User;
import com.example.spring.api.entity.UserModel;
import com.example.spring.api.sevice.CustomUserDetailsService;
import com.example.spring.api.sevice.UserService;
import com.example.spring.api.util.JwtTokenUtil;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
     private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel model,HttpServletRequest req){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(model.getEmail(),model.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //Used for session based authorization
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(authentication);
//        HttpSession session = req.getSession(true);
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        //for jwt based authorization
        final UserDetails userDetails=userDetailsService.loadUserByUsername(model.getEmail());
        final String jwtToken=jwtTokenUtil.generateToken(userDetails);


        return new ResponseEntity<JwtResponse>(new JwtResponse(jwtToken), HttpStatus.OK);
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
