package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer,Long> {
}
