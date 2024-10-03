package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Customer;
import com.milestone4.ticketplatform.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    //Index Service method
    public List<Customer> findAll(){
        return repository.findAll();
    }
}
