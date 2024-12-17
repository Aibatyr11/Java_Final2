package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService, TaskService taskService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping
    public String listCategories(Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        //List<Category> categories = categoryService.findByUser(user);


        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories/list"; // Ссылка на categories/list.html
    }


    @GetMapping("/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }


    @PostMapping
    public String saveCategory(@ModelAttribute Category category, Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        try {
            categoryService.saveCategory(category, user);
            return "redirect:/categories";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("category", category);
            return "categories/form";
        }
    }


    @GetMapping("/{id}")
    public String showCategoryTasks(@PathVariable Long id, Model model) {

        Category category = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));


        List<Task> tasks = taskService.findByCategory(category);


        model.addAttribute("category", category);
        model.addAttribute("tasks", tasks);


        return "tasks/list";
    }
}
