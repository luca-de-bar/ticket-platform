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
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketService service;

    //List all ticket, sorted by recent
    @GetMapping("/sort-recent")
    public ResponseEntity<List <Ticket>> indexSortRecent (){
        List<Ticket> result;
        result = service.findAllSortedByRecent();
        if(result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //List all ticket, sorted by category
    @GetMapping("/sort-category")
    public ResponseEntity<List <Ticket>> indexSortCategory(@RequestParam(name = "category", required = true) Long id){
        List<Ticket> result;
        result = service.findByCategory(id);
        if(result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //List all ticket, sorted by status
    @GetMapping("/sort-status")
    public ResponseEntity<List <Ticket>> indexSortStatus(@RequestParam(name = "status", required = true) String status){
        List<Ticket> result;
        result = service.findByStatus(status);
        if(result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
