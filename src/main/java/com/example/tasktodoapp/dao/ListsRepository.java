package com.example.tasktodoapp.dao;

import com.example.tasktodoapp.entity.List;
import com.example.tasktodoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListsRepository extends JpaRepository<List, Integer> {

    @Query( "select u from List u inner join u.user r where r.username=:username" )
    java.util.List<List> findByUsername(@Param("username") String username);
    java.util.List<List> findByUser(User user);

}
