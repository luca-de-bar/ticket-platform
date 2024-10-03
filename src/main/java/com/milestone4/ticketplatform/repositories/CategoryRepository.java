package com.milestone4.ticketplatform.repositories;

import com.milestone4.ticketplatform.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category,Long> {
}
