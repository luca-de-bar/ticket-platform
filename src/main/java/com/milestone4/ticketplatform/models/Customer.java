package com.milestone4.ticketplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String customerType;

    @NotNull
    private String customerDetails;

    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets;

    public Customer(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotNull String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(@NotNull String customerType) {
        this.customerType = customerType;
    }

    public @NotNull String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(@NotNull String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
