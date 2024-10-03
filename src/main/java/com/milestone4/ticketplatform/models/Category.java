package com.milestone4.ticketplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String categoryName;

    @NotNull
    private boolean isActive;

    //FK ticket-id
    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<Ticket> tickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty @NotNull String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotEmpty @NotNull String categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull
    public boolean isActive() {
        return isActive;
    }

    public void setActive(@NotNull boolean active) {
        isActive = active;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
