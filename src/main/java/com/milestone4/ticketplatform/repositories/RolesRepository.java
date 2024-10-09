package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
}
