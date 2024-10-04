package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

 @Repository
public interface OperatorRepository extends JpaRepository <Operator,Long> {

     public Optional<Operator> findByUsername(String username);

     public List<Operator> findByActiveTrue();
}
