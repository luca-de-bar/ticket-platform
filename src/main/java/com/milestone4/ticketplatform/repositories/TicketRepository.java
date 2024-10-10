package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> getAllByOperatorEquals(Operator operator);
    List<Ticket> findAllByTitleContainingIgnoreCase(String title);


    //API
    List <Ticket> getAllByCategoryId(Long id);
    List<Ticket> getAllByStatusEqualsIgnoreCase(String status);
}
