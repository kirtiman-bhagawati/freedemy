package com.freedemy.api.repositories;

import com.freedemy.api.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findByUsername(String username);
}
