package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Nota;
import com.milestone4.ticketplatform.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotesService {

    @Autowired
    private NotesRepository repository;

    //Save a note
    public Nota save(Nota note){
        return repository.save(note);
    }
}
