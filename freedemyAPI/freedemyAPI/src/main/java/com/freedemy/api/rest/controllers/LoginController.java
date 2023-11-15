package com.freedemy.api.rest.controllers;

import com.freedemy.api.models.Customer;
import com.freedemy.api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//CrossOrigin annotation to override the CORS layer of the browser
//instead of asterisk, can have a full url like https://localhost:9999/something
//asterisk means all the urls are allowed to communicate with the API
//this is not a recommended way, as it'll be tedious to add this annotation to all the controllers
//recommended way is to create a global configuration in the SecurityFilterChain bean
//@CrossOrigin(origins = "*")
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

    @GetMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        List<Customer> customers = customerRepository.findByUsername(authentication.getName());
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            return null;
        }
    }
}
