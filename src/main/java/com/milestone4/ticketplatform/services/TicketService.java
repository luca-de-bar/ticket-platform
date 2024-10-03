package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    //Index Service method
    public List<Ticket> findAllSortedByRecent(){
        return repository.findAll(Sort.by("creationDate"));
    }

    public Ticket store (Ticket ticket){
        return repository.save(ticket);
    }
}
