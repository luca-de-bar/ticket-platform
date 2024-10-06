package com.milestone4.ticketplatform.controllers;


import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/{id}")
    public String personalArea(@PathVariable("id") Long id, Model model, Authentication authentication){
        model.addAttribute("auth", authentication);
        model.addAttribute("operator",operatorService.findById(id));
        return "operators/info";
    }

    @PostMapping("/{id}")
    public String changeStatus(@ModelAttribute("operator") Operator formOperator,
                               RedirectAttributes attributes){

        if(formOperator.isActive()){
            operatorService.update(formOperator);
            attributes.addFlashAttribute("operatorSuccess","Stato aggiornato correttamente!");
        } else {
            if (!formOperator.getTickets().isEmpty()){
                //forEach per ogni ticket, se Da fare impedisci.
                for(Ticket ticket : formOperator.getTickets()){
                    if(ticket.getStatus().equals("Da Fare") || ticket.getStatus().equals("In corso")){
                        attributes.addFlashAttribute("operatorAlert","Spiacente, hai ancora dei ticket da completare");
                        break;
                    }
                }
            } else {
                operatorService.update(formOperator);
                attributes.addFlashAttribute("operatorSuccess","Stato aggiornato correttamente!");
            }
        }
        return "redirect:/operator/{id}";
    }
}
