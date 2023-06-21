package com.example.tasktodoapp.dao;

import com.example.tasktodoapp.entity.Comment;
import com.example.tasktodoapp.entity.List;
import com.example.tasktodoapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentsRepository extends JpaRepository<Comment,Integer> {

    java.util.List<Comment> findByTask(Task task);

    @Query( "select u from Comment u inner join u.task r where r.id=:id" )
    java.util.List<Comment> findByTaskId(@Param("id") int id);
}
