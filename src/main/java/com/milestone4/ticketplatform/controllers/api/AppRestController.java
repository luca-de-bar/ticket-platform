package com.milestone4.ticketplatform.controllers.api;

import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AppRestController {

    @Autowired
    private TicketService service;

    //HEALTH CHECK
    @GetMapping
    public String healthCheck(){
        return "Situation normal, all fired up";
    }

    //INDEX
    @GetMapping("/tickets-all")
    public ResponseEntity<List <Ticket>> getAllTickets(){
        //Create empty list, populate with JPA
        List<Ticket> response = service.findAllSortedByRecent();
        if(response.isEmpty()){
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //SHOW
    @GetMapping("/ticket-id")
    public ResponseEntity<Ticket> getTicketById(@RequestParam("id") Long id){
        Ticket response = service.findById(id);
        if(response.getId() == null){
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
