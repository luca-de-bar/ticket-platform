package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository <Nota,Long> {
}
