package com.milestone4.ticketplatform.controllers;


import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Role;
import com.milestone4.ticketplatform.repositories.RolesRepository;
import com.milestone4.ticketplatform.services.OperatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RolesRepository repository;

    @Autowired
    private OperatorService service;

    @GetMapping
    public String register(Model model){
        model.addAttribute("operator", new Operator());
        model.addAttribute("roles",repository.findAll());
        return "security/register";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute("operator") Operator formOperator,
                        BindingResult bindingResult,
                        RedirectAttributes attributes,
                        Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("formOperator",formOperator);
            attributes.addFlashAttribute("operatorDanger","Non è stato possibile creare l'operatore.");
        }


        for (Role role : formOperator.getRoles()){
            //If operator registered is admin
            if (role.getId() == 0){
                formOperator.setActive(false);
            } else {
                formOperator.setActive(true);
            }
        }
        service.encodePassword(formOperator);
        service.store(formOperator);
        attributes.addFlashAttribute("operatorSuccess","Utente creato correttamente!");
        return "redirect:/login";
    }
}
