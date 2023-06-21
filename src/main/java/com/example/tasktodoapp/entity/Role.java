package com.example.tasktodoapp.entity;


import jakarta.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rolename")
    private String rolename;


    public Role() {

    }


}
