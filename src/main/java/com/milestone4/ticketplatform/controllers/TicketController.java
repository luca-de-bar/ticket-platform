package com.milestone4.ticketplatform.controllers;

import com.milestone4.ticketplatform.models.Nota;
import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NotesService notesService;

    //index
    @GetMapping
    public String index(Model model, Authentication authentication){
        model.addAttribute("tickets",ticketService.findAllSortedByRecent());
        model.addAttribute("username",authentication);
        model.addAttribute("operators",operatorService.findAll());

        //pass current operator for status change button
        Optional<Operator> operator = operatorService.findByUsername(authentication.getName());
        model.addAttribute("operator",operator);
        return "/main/index";
    }

    //create + store
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("operators",operatorService.findActiveOperators());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("customers",customerService.findAll());
        return "/main/create";
    }

    @PostMapping("/create")
    public String store (@Valid @ModelAttribute("ticket") Ticket formTicket,
                         BindingResult bindingResult,
                         RedirectAttributes attributes,
                         Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("ticket",formTicket);
            model.addAttribute("operators",operatorService.findAll());
            model.addAttribute("categories",categoryService.findAll());
            model.addAttribute("customers",customerService.findAll());
            return "/main/create";
        } else {
            ticketService.store(formTicket);
            attributes.addFlashAttribute("successMessage","Il Ticket " + formTicket.getId() + " è stato creato");
            return "redirect:/";
        }
    }

    //Show
    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        model.addAttribute("ticket",ticketService.findById(id));
        model.addAttribute("operators",operatorService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("customers",customerService.findAll());

        //Nuova istanza di Nota per la creazione di nuove note
        model.addAttribute("nota",new Nota());

        //Trovo le note del ticket specifico
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("notes",ticket.getNotes());

        return "/main/show";
    }

    //Creazione nuova nota
    @PostMapping("/note/{tid}")
    public String addNote(@PathVariable("tid") Long ticketID,
                          @ModelAttribute("nota") Nota formNota,
                          Authentication authentication){

        //Trovo UserID corrente
        Operator operator = operatorService.findByUsername(authentication.getName()).get();

        //Set operatore e ticketID
        formNota.setOperator(operator);
        formNota.setTicket(ticketService.findById(ticketID));

        //Salvo la nota.
        notesService.save(formNota);
        return "redirect:/";
    }

    //edit + update
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, Authentication authentication){
        model.addAttribute("ticket",ticketService.findById(id));
        model.addAttribute("operators",operatorService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("customers",customerService.findAll());
        model.addAttribute("username", authentication);
        return "/main/edit";
    }

    @PostMapping("/edit/{id}")
    public String update (@Valid @ModelAttribute("ticket") Ticket formTicket,
                          BindingResult bindingResult,
                          RedirectAttributes attributes,
                          Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("ticket",formTicket);
            model.addAttribute("operators",operatorService.findAll());
            model.addAttribute("categories",categoryService.findAll());
            model.addAttribute("customers",customerService.findAll());
            return "/main/edit";
        }
            ticketService.update(formTicket);
            attributes.addFlashAttribute("successMessage","il Ticket " + formTicket.getId() + " è stato modificato con successo");
            return "redirect:/";
    }

    //delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes attributes){
        ticketService.delete(id);
        attributes.addFlashAttribute("dangerMessage","Il Ticket " + id + " è stato eliminato correttamente");
        return "redirect:/";
    }
}
