package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Ticket;
import com.milestone4.ticketplatform.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorService{

    @Autowired
    private OperatorRepository repository;

    //Index Service method
    public List<Operator> findAll(){
        return repository.findAll(Sort.by("username"));
    }

    //Active operators
    public List<Operator>findActiveOperators(){
        return repository.findByActiveTrue();
    }

    //Find by id
    public Operator findById(Long id){
        return repository.findById(id).get();
    }

    //Find by username
    public Optional <Operator> findByUsername(String username){
        return repository.findByUsername(username);
    }

    //Update Operator
    public Operator update (Operator operator){
        return repository.save(operator);
    }

    //From Optional to Operator
    public Operator optionalToOperator(Optional<Operator> operator){
        return operator.get();
    }


    //Stato operatore
    public void checkAndUpdateStatus(Operator operator) throws Exception{
        //Se operatore si vuole disattivare
        if (!operator.isActive()){
           //Se operatore ha ticket
            if( !operator.getTickets().isEmpty()){
               for (Ticket ticket : operator.getTickets()){
                   //Lancio eccezione se 'Da Fare' o 'In Corso'
                   if(ticket.getStatus().equals("Da Fare") || ticket.getStatus().equals("In Corso")){
                       throw new Exception("Hai ticket da completare!");
                   }
               }
           }
       }
        repository.save(operator);
    }

    //Save a new operator
    public Operator store(Operator operator){
        return repository.save(operator);
    }

    //Encode password for new operators
    public void encodePassword(Operator operator){
        operator.setPassword("{noop}" + operator.getPassword());
    }
}
