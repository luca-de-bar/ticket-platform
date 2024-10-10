package com.milestone4.ticketplatform.api;

import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketRestController {

    @Autowired
    private TicketService service;

    //Define ArrayList of Tickets
    List<Ticket> result;

    @GetMapping("/tickets")
    public List<Ticket> indexSortRecent (){
        result = service.findAllSortedByRecent();
        return result;
    }
}
