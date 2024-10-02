package com.milestone4.ticketplatform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TicketController {

    @GetMapping
    public String index(){
        return "/tickets/index";
    }
}
