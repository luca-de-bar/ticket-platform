package com.milestone4.ticketplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String statoTicket;

    @CreationTimestamp
    private LocalDateTime dataCreazione;

    //FK Operator ID
    @ManyToOne
    @JoinColumn(name = "operator_id",nullable = false)
    private Operator operator;

    //FK Notes ID
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    private List<Notes> notes;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty @NotNull String getStatoTicket() {
        return statoTicket;
    }

    public void setStatoTicket(@NotEmpty @NotNull String statoTicket) {
        this.statoTicket = statoTicket;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
