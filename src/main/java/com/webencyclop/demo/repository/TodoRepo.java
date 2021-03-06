package com.webencyclop.demo.repository;


import com.webencyclop.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepo extends JpaRepository<Todo, Long> {
    List<Todo> findByUserName(String user);
}