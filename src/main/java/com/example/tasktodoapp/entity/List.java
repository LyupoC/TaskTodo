package com.example.tasktodoapp.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;

@Entity
@Data
@Table(name = "list")
public class List {

    @Id
    @Column(name = "list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;


    @OrderBy("priority, title")
    @OneToMany(mappedBy = "list", cascade = {CascadeType.REMOVE, CascadeType.DETACH})
    private java.util.List<Task> tasks = new ArrayList<>();

    @NotBlank(message = "The title Cannot be blank")
    @Length(max = 50)
    @Column(name = "title")
    private String title;

    @Length(max = 250)
    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "updated_by")
    private int updatedBy;


    @Column(name = "status")
    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Calendar createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Calendar updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "deadline_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Calendar deadlineDate;

    @Column(name = "planned_start_date")
    @Temporal(TemporalType.DATE)
    private Calendar plannedStartDate;

    @PrePersist
    private void init() {
        setCreatedDate(Calendar.getInstance());
        setUpdatedDate(Calendar.getInstance());

    }

    public List() {
    }

    public List(String title, String description, int createdBy, int updatedBy, Calendar plannedStartDate) {

        this.title = title;
        this.description = description;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.plannedStartDate = plannedStartDate;

    }

    public String toString() {
        return "List: " + this.title + ", description: " + this.description + " ,startdate:  " + this.plannedStartDate + " ,deadline: ";
    }
}
