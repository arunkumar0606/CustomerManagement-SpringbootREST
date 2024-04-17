package com.example.spring.api.sevice;

import com.example.spring.api.entity.User;
import com.example.spring.api.entity.UserModel;
import com.example.spring.api.exception.CustomerNotFoundException;
import com.example.spring.api.exception.EmailAlreadyExistException;
import com.example.spring.api.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserModel userModel){
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new EmailAlreadyExistException("Email ID is already Registered. Please use different Email");
        }
        User user =new User();
        BeanUtils.copyProperties(userModel, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
     //   System.out.println(passwordEncoder.encode(user.getPassword()));
       // System.out.println(passwordEncoder.matches(user.getPassword(),passwordEncoder.encode(user.getPassword())));
        return userRepository.save(user);
    }


    public User getUser(long id) {
        if(userRepository.existsById(id)){
            return userRepository.findById(id);
        }
        else{
            throw new CustomerNotFoundException("User Not found for the given ID");
        }
    }

    public User updateUser(int id, UserModel userModel) {
        if(userRepository.existsById(id)){
            User newUser=userRepository.findById(id);
            newUser.setName(userModel.getName());
            newUser.setEmail(userModel.getEmail());
            newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
            newUser.setAge(userModel.getAge());
            return userRepository.save(newUser);
        }
        throw new CustomerNotFoundException("Could not find entered user ID");
    }

    public void deleteUserById(long id) {
        if(userRepository.existsById(id)){
            userRepository.delete(userRepository.findById(id));
        }
        else{
            throw new CustomerNotFoundException("Could not find the provided user with used id :"+id);
        }
    }

    public User getLoggedInUser(){
        String userEmail=SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return userRepository.findByEmail(userEmail);
        }
        catch (Exception e){
            throw new CustomerNotFoundException("User not Found : "+userEmail);
        }
    }
}
