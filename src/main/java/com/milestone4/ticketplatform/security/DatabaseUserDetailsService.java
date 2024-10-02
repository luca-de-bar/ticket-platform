package com.milestone4.ticketplatform.security;

import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    public OperatorRepository operatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Operator> operator = operatorRepository.findByUsername(username);

        if(operator.isPresent()){
            return new DatabaseUserDetails((operator.get()));
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
