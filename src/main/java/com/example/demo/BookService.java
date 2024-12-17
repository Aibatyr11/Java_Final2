package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByUserAndPriority(User user, int priority) {
        return taskRepository.findByUserAndPriority(user, priority);
    }

    public List<Task> findByUserAndCategory(User user, Category category) {
        return taskRepository.findByUserAndCategory(user, category);
    }

    public List<Task> findByCategory(Category category) {
        return taskRepository.findByCategory(category);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();  // Вызываем метод findAll() у репозитория
    }


}

