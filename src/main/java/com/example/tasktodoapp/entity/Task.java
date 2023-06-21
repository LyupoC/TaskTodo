package com.example.tasktodoapp.entity;

import com.example.tasktodoapp.annot.DateRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;


@Entity
@Data
@Table(name = "task")
@DateRange(date = "deadlineDate", startDate = "list.createdDate", endDate = "list.deadlineDate", message = "Task deadline should be before list deadline and after date of creation")

public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private List list;


    @OneToMany(mappedBy = "task", cascade = {CascadeType.REMOVE, CascadeType.DETACH})
    private java.util.List<Comment> comments = new ArrayList<>();

    @Length(max = 60)
    @Column(name = "title")
    @NotBlank(message = "Task title cannot be blank")
    private String title;

    @Length(max = 500)
    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "updated_by")
    private int updatedBy;

    @Column(name = "status")
    private int status;

    @Column(name = "priority")
    private int priority;

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


    private Calendar deadlineDate;

    @Column(name = "planned_start_date")
    @Temporal(TemporalType.DATE)
    private Calendar plannedStartDate;


    @PrePersist
    private void init() {
        setCreatedDate(Calendar.getInstance());
        setUpdatedDate(Calendar.getInstance());

        if (deadlineDate == null) {
            setDeadlineDate(list.getDeadlineDate());
        }

        if (list.getId() == 0) {
            return;
        }
    }

    public Task() {
    }

    public Task(String title, String description, int createdBy, int updatedBy, Calendar plannedStartDate) {

        this.title = title;
        this.description = description;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.plannedStartDate = plannedStartDate;

    }

    public String toString() {


        return "Task: " + this.title + ", description: " + this.description + " ,startdate:  " + this.plannedStartDate + " ,deadline: " + this.deadlineDate.toString();
    }

    public String getCommentsJSON() {

        StringBuffer commentsJSON = new StringBuffer();
        commentsJSON.append("[");
        for (int i = 0; i < comments.size(); i++) {
            commentsJSON.append(comments.get(i).toJSON());

            if (i != comments.size() - 1)
                commentsJSON.append(",");
        }

        commentsJSON.append("]");

        return commentsJSON.toString();
    }
}
