package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndPriority(User user, int priority);
    List<Task> findByUserAndCategory(User user, Category category);
    List<Task> findByCategory(Category category);
}
