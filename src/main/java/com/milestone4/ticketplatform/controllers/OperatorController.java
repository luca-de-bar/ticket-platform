package com.milestone4.ticketplatform.controllers;


import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.repositories.RolesRepository;
import com.milestone4.ticketplatform.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    RolesRepository rolesRepository;

    @GetMapping("/{id}")
    public String personalArea(@PathVariable("id") Long id, Model model){
        model.addAttribute("operator",operatorService.findById(id));
        model.addAttribute("roles",rolesRepository.findAll());
        return "operators/info";
    }

    @PostMapping("/{id}")
    public String updateStatus(@ModelAttribute("operator") Operator formOperator,
                               RedirectAttributes attributes){
        try {
            operatorService.checkAndUpdateStatus(formOperator);
            attributes.addFlashAttribute("operatorSuccess","Status cambiato correttamente!");
        } catch (Exception e) {
            attributes.addFlashAttribute("operatorAlert",e.getMessage());
        }

        return "redirect:/operator/{id}";
    }
}
