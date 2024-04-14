package com.example.spring.api.repository;

import com.example.spring.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);



    User findByEmail(String email);

    boolean existsById(long id);

    User findById(long id);
}
