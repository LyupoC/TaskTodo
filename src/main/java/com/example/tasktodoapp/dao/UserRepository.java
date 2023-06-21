package com.example.tasktodoapp.dao;

import com.example.tasktodoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
   public User findByUsername(String username);
}
