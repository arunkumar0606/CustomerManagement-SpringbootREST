package com.example.spring.api.controller;

import com.example.spring.api.entity.Customer;
import com.example.spring.api.sevice.CustomerService;
import com.example.spring.api.sevice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @GetMapping("/about")
    public String hello(){
        return "Hello !";
    }
    @PostMapping("/addcustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addcustomer(@RequestBody @Valid Customer data){
        return customerService.addCustomer(data);
    }

    @PostMapping("/addcustomers")
    public ResponseEntity<Object> addcustomers(@RequestBody @Valid List<Customer> datas){
        return customerService.addCustomers(datas);

    }

    @GetMapping("/getCustomers")
    public ResponseEntity<Object> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/getCustomersBySort/{field}")
    public List<Customer> getCustomersBySort(@PathVariable String field){
        return customerService.getCustomersBySort(field);
    }

    @GetMapping("/getCustomersByPage/{page}/{size}")
    public List<Customer> getCustomersByPaging(@PathVariable("page") int page,@PathVariable("size") int size){
        return customerService.getCustomersByPaging(page,size);
    }
    @GetMapping("/getCustomersByPage")
    public List<Customer> getCustomersByPage(Pageable page){
        return customerService.getCustomersByPage(page).toList();
    }


    @GetMapping("/getCustomers/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable int id){
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getCustomersbyparam")
    public ResponseEntity<Object> getCustomerByRp(@RequestParam int id){
        return customerService.getCustomerByRequestParam(id);
    }

    @GetMapping("/getCustomerByUserId")
    public List<Customer> getCustomerByUserId(){
        long userId=userService.getLoggedInUser().getId();
        return customerService.getCustomersByUserId(userId);
    }

    @GetMapping("/getCustomerByUserIdAndId/{rollNo}")
    public List<Customer> getCustomerByUserIdAndId(@PathVariable int rollNo){
        long userId=userService.getLoggedInUser().getId();
        return customerService.getCustomersByUserIdAndRollNo(userId,rollNo);
    }




    /*
    Filter methods
     */
    @GetMapping("/getCustomersbyAge/{age}")
    public ResponseEntity<Object> getCustomerByAge(@PathVariable int age){
        return customerService.getCustomerByAge(age);
    }

    @GetMapping("/getCustomersbyName/{name}")
    public ResponseEntity<Object> getCustomerByName(@PathVariable String name){
        return customerService.getCustomerByName(name);
    }


    @PutMapping("/addcustomer/update/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int id){
        return customerService.updateCustomer(id);
    }

    @PutMapping("/addcustomer/updatebybody/{id}")
    public ResponseEntity<Object> updateCustomers(@RequestBody Customer data,@PathVariable int id){
        return customerService.updateCustomerByBody(data,id);
    }

    /*
    Delete
     */


    @DeleteMapping("/deleteCustomerById/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id){
        customerService.deleteCustomerByRollNo(id);
    }

    @DeleteMapping("/deleteCustomerByName/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String name){
        customerService.deleteCustomerByname(name);
    }

}
