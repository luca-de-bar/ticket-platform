package com.milestone4.ticketplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String status;

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private LocalDateTime closingDate;

    private String priority;

    private String report;

    @ManyToOne
    @JoinColumn(name = "operator_id",nullable = false)
    //@Formula("SELECT * FROM operators where stato_operatore='true'")
    private Operator operator;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    private List<Nota> notes;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty @NotNull String getStatus() {
        return status;
    }

    public void setStatus(@NotEmpty @NotNull String status) {
        this.status = status;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public List<Nota> getNotes() {
        return notes;
    }

    public void setNotes(List<Nota> notes) {
        this.notes = notes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public @NotEmpty @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty @NotNull String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public String getFormattedCreationDate(){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return creationDate.format(FORMATTER);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public String getFormattedClosingDate(){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return closingDate.format(FORMATTER);
    }

    public void setClosingDate() {
        this.closingDate = LocalDateTime.now();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
