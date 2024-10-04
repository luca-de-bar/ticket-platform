package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    //List all ticket, sorted by creation date
    public List<Ticket> findAllSortedByRecent(){
        return repository.findAll();
    }

    //Get ticket by ID
    public Ticket findById(Long id){
        return repository.findById(id).get();
    }

    //Save a new ticket
    public Ticket store (Ticket ticket){
        return repository.save(ticket);
    }

    //Delete a ticket
    public void delete(Long id){
        repository.deleteById(id);
    }
}
