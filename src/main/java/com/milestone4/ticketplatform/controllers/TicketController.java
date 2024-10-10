package com.milestone4.ticketplatform.controllers;

import com.milestone4.ticketplatform.models.Nota;
import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.security.DatabaseUserDetails;
import com.milestone4.ticketplatform.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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

        //Find current logged user
        Optional <Operator> optionalOperator = operatorService.findByUsername(authentication.getName());
        Operator loggedOperator = operatorService.optionalToOperator(optionalOperator);

        //Pass logged user to findTicketsByRole
        //shows all tickets to admin and only assigned ticket to operators.
        List<Ticket> tickets = ticketService.findTicketsByRole(loggedOperator);
        model.addAttribute("tickets", tickets);
        model.addAttribute("operator",loggedOperator);
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
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
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
                          Authentication authentication,
                          RedirectAttributes attributes){

        //Trovo UserID corrente
        Operator operator = operatorService.findByUsername(authentication.getName()).get();

        //Set operatore e ticketID
        formNota.setOperator(operator);
        formNota.setTicket(ticketService.findById(ticketID));

        //Salvo la nota.
        notesService.save(formNota);
        attributes.addFlashAttribute("successMessage","La nota è stata aggiunta correttamente al ticket " + formNota.getTicket().getId());
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

    @GetMapping("/search")
    public String search(@RequestParam(value = "title", required = false) String title,
                         Model model,
                         Authentication authentication){

        //Find current user authenticated
        Optional <Operator> optionalOperator = operatorService.findByUsername(authentication.getName());
        Operator loggedOperator = operatorService.optionalToOperator(optionalOperator);

        model.addAttribute("operator",loggedOperator);

        List<Ticket> tickets;
        if(title == null || title.isEmpty()){
            tickets = ticketService.findAllSortedByRecent();
        } else {
            tickets=  ticketService.findByTitle(title);
        }
        model.addAttribute("tickets",tickets);
        return "/main/index";
    }
}
