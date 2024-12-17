package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public void saveCategory(Category category, User user) {
        category.setUser(user); // Привязать категорию к пользователю
        if (categoryRepository.findByNameAndUser(category.getName(), user).isPresent()) {
            throw new IllegalArgumentException("Category name already exists for this user");
        }
        categoryRepository.save(category);
    }

    public List<Category> findByUser(User user) {
        return categoryRepository.findByUser(user);
    }


    public Optional<Category> findByNameAndUser(String name, User user) {
        return categoryRepository.findByNameAndUser(name, user);
    }


    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
