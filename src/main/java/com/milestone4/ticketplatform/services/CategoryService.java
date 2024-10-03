package com.milestone4.ticketplatform.services;

import com.milestone4.ticketplatform.models.Category;
import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    //Index Service method
    public List<Category> findAll(){
        return repository.findAll(Sort.by("name"));
    }

    public Category findById(Category category){
        return repository.findById(category.getId()).get();
    }
}
