package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository repository;

    //Index Service method
    public List<Operator> findAll(){
        return repository.findAll(Sort.by("username"));
    }

    public List<Operator>findActiveOperators(){
        return repository.findByActiveTrue();
    }
}
