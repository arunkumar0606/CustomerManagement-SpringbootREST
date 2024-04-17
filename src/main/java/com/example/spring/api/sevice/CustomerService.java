package com.example.spring.api.sevice;

import com.example.spring.api.entity.Customer;
import com.example.spring.api.exception.CustomerNotFoundException;
import com.example.spring.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repo;

    @Autowired
    UserService userService;

    /*
    ADD customers
     */
    public Customer addCustomer(Customer customer){
        customer.setUser(userService.getLoggedInUser());
        return repo.save(customer);
    }

    public ResponseEntity<Object> addCustomers(List<Customer> customers){
        for (Customer customer:customers) {
            customer.setUser(userService.getLoggedInUser());
        }
         repo.saveAll(customers);
         return ResponseEntity.status(HttpStatus.OK).body(repo.findAll());
    }

    /*
   GET customers
    */
    public List<Customer> getAll() {
        return repo.findAll();
    }

    public ResponseEntity<Object> getCustomers() {
        if(repo.findAll().isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No entries found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repo.findByUserId(userService.getLoggedInUser().getId()));
    }

    public List<Customer> getCustomersBySort(String field) {
        return repo.findAll(Sort.by(Sort.Direction.ASC,field));
    }
    public List<Customer> getCustomersByPaging(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }
    public Page<Customer> getCustomersByPage(Pageable page) {
        return repo.findAll(page);
    }


    public ResponseEntity<Object> getCustomerById(int id) throws CustomerNotFoundException{
        List<Customer> res=repo.findById(id);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.OK).body(repo.findById(id));
        }
        throw  new CustomerNotFoundException("Customer not found for ID "+id);
    }

    public List<Customer> getCustomersByUserId(long userId){
        return repo.findByUserId(userId);
    }

    public List<Customer> getCustomersByUserIdAndRollNo(long userId,int rollNo){
        if(repo.findByUserIdAndRollNo(userId,rollNo).isEmpty()){
            throw new CustomerNotFoundException("Value not found for the Current User");
        }
        return repo.findByUserIdAndRollNo(userId,rollNo);
    }

    public ResponseEntity<Object> updateCustomer(int id) {
        Customer customer=repo.findById(id).get(0);
        customer.setName("Updated "+ customer.getName());
        repo.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body(repo.findAll());

    }

    public ResponseEntity<Object> updateCustomerByBody(Customer data,int id) {
        try {
            Customer originalCustomer = repo.findById(id).get(0);
            originalCustomer.setName(data.getName());
            originalCustomer.setCompany(data.getCompany());
            originalCustomer.setRollNo(data.getRollNo());
            repo.save(originalCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(repo.findAll());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inavlid data Given");
        }
    }

    public ResponseEntity<Object> getCustomerByRequestParam(int id) {
        return ResponseEntity.status(HttpStatus.OK).body(repo.findById(id));
    }


    public ResponseEntity<Object> getCustomerByAge(int age) {
        if(!repo.existsById(age)){
            throw new CustomerNotFoundException("Id not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repo.findByAge(age));
    }

    public ResponseEntity<Object> getCustomerByName(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(repo.findByNameStartingWith(name));
    }

    public void deleteCustomerByRollNo(int rollno) {
        if(repo.existsById(rollno)){
            repo.deleteById(rollno);
        }
        else {
            throw new CustomerNotFoundException("Id not found");
        }
    }

    public void deleteCustomerByname(String name) {
        List<Customer> c=repo.findByNameStartingWith(name);
        if(!c.isEmpty()){
            repo.deleteAll(c);
        }
        else {
            throw new CustomerNotFoundException("Name not found");
        }
    }
}
