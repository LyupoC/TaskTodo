package com.example.tasktodoapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;


    @Column(name = "email")
    private String email;


    @Column(name = "profile_info")
    private String userInfo;


    @Column(name = "enabled")
    private int status;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<List> lists = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))

    private Set<Role> roles = new HashSet<>();


    public User() {

    }

    public User(String userName, String email, String userInfo, int status
    ) {
        this.username = userName;
        this.userInfo = userInfo;
        this.email = email;
        this.status = status;
    }

    public String toString() {
        return "User: " + this.username + " , " + this.email + " , " + this.userInfo;
    }


}
