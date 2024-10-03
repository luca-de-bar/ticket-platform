package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;
}
