package com.example.tasktodoapp.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "list_id")
    private List list;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar dateCreated;

    @Column(name = "updated_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar dateUpdated;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @PrePersist
    private void init() {

        setDateCreated(Calendar.getInstance());
        setDateUpdated(Calendar.getInstance());

    }

    private String parseCalendarToString(Calendar calendar) {

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 1);

        Date date = cal.getTime();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        return format1.format(date);

    }

    public String toJSON() {
        return "{\"id\":" + id + ",\"content\":" + "\"" + content.trim() + "\"" + ",\"user\":" + "\"" + user.getUsername() + "\"" + ",\"dateCreated\":" + "\"" + parseCalendarToString(dateCreated) + "\"" + "}";
    }

}
