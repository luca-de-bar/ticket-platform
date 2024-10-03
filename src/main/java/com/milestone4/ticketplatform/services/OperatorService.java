package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository repository;
}
