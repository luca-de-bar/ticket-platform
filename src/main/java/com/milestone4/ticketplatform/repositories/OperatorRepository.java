package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OperatorRepository extends JpaRepository <Operator,Long> {
    public Optional<Operator> findByUsername(String username);
}
