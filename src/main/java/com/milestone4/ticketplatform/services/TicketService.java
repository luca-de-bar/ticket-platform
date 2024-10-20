package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Role;
import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //Get Optional Ticket ID
    public Optional<Ticket> findOptionalTicket(Long id){
        return repository.findById(id);
    }

    //Save a new ticket
    public Ticket store (Ticket ticket){
        //If ticket opened with status "In Corso"
        if(ticket.getStatus().equals("In Corso")){
            ticket.getOperator().setActive(false);
        }
        return repository.save(ticket);
    }

    //Update a new ticket
    public Ticket update (Ticket ticket){
        //If ticket.status "In Corso" :
        if (ticket.getStatus().equals("In Corso")){
            ticket.getOperator().setActive(false);
        }
        //If ticket.status "Completato" :
        if(ticket.getStatus().equals("Completato")){
            ticket.getOperator().setActive(true);
            //Set closing date to LocalDate.now()
            ticket.setClosingDate();
        }
        return repository.save(ticket);
    }

    //Delete a ticket
    public void delete(Long id){
        repository.deleteById(id);
    }

    //Find By Operator
    public List <Ticket> findByOperator (Operator operator){
        return repository.getAllByOperatorEquals(operator);
    }

    //Find tickets by Assigned Operator
    public List<Ticket> findTicketsByRole (Operator operator){
        //if admin : see all tickets, otherwise see only assigned tickets.
        for (Role role : operator.getRoles()){
            if (role.getName().equals("Admin")){
                return findAllSortedByRecent();
            }
        }
        return findByOperator(operator);
    }

    //Search Form
    public List<Ticket>findByTitle(String title){
        return repository.findAllByTitleContainingIgnoreCase(title);
    }

    //Tickets by category
    public List<Ticket>findByCategory(Long id){
        return repository.getAllByCategoryId(id);
    }

    public List<Ticket> findByStatus(String status) {
        return repository.getAllByStatusEqualsIgnoreCase(status);
    }
}
