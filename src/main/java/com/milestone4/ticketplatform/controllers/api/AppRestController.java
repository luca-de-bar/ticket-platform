package com.milestone4.ticketplatform.controllers.api;

import com.milestone4.ticketplatform.models.Customer;
import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.services.CustomerService;
import com.milestone4.ticketplatform.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AppRestController {

    @Autowired
    private TicketService service;

    @Autowired
    private CustomerService customerService;

    //HEALTH CHECK
    @GetMapping
    public String healthCheck(){
        return "Situation normal, all fired up";
    }

    //INDEX
    @GetMapping("/tickets-all")
    public ResponseEntity<List <Ticket>> index(){
        //Create empty list, populate with JPA
        List<Ticket> response = service.findAllSortedByRecent();
        if(response.isEmpty()){
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //SHOW
    @GetMapping("/ticket-{id}")
    public ResponseEntity<Ticket> get(@PathVariable("id") Long id){
        Optional<Ticket>response = service.findOptionalTicket(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //CREATE
    @PostMapping( "/ticket-new")
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket){
        Ticket newTicket = service.store(ticket);
        return new ResponseEntity<>(newTicket,HttpStatus.CREATED);
    }

    //CREATE CUSTOMER
    @PostMapping(value = "/customer-new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer newCustomer = customerService.store(customer);
        return new ResponseEntity<>(newCustomer,HttpStatus.CREATED);
    }

    @PostMapping(value = "/debug-customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> debugCustomer(@RequestBody String customerJson) {
        System.out.println(customerJson);
        return new ResponseEntity<>("JSON Received", HttpStatus.OK);
    }


}
