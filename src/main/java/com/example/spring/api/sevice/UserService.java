package com.example.spring.api.sevice;

import com.example.spring.api.entity.User;
import com.example.spring.api.entity.UserModel;
import com.example.spring.api.exception.EmailAlreadyExistException;
import com.example.spring.api.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserModel userModel){
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new EmailAlreadyExistException("Email ID is already Registered. Please use different Email");
        }
        User user =new User();
        BeanUtils.copyProperties(userModel, user);
        return userRepository.save(user);
    }


}
