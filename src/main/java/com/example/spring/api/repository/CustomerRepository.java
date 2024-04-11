package com.example.spring.api.repository;

import com.example.spring.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public List<Customer> findById(int rollno);

    List<Customer> findByAge(int age);

    List<Customer> findByNameLike(String name);

    List<Customer> findByNameStartingWith(String name);
}
