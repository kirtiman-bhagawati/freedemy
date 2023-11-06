package com.freedemy.api.rest.controllers;

import com.freedemy.api.models.Customer;
import com.freedemy.api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Customer customer){
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try{
            String hashedPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(hashedPassword);
            savedCustomer = customerRepository.save(customer);
            if(savedCustomer.getCustomerId()>0){
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("User successfully registered");
            }
        }catch (Exception e){
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception occured: "+e);
        }

        return response;
    }
}
