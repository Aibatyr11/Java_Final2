package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private GenreService categoryService;

    @GetMapping
    public String listTasks(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<Task> tasks = taskService.findByUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }
    @GetMapping("/list")
    public String listAllTasks(Model model) {
        List<Task> tasks = taskService.findAll(); // Получаем все задачи
        model.addAttribute("tasks", tasks);
        return "tasks/list"; // Страница для отображения всех задач
    }

    @GetMapping("/new")
    public String newTask(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categories);

        // model.addAttribute("categories", categoryService.findByUser(user));
        return "tasks/form";
    }


    @PostMapping
    public String saveTask(@ModelAttribute Task task, @RequestParam("categoryId") Long categoryId, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));




        if (task.getPriority() == null) {
            task.setPriority(1);
        }
        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now());
        }


        task.setUser(user);
        task.setCategory(category);


        taskService.save(task);
        return "redirect:/tasks";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model, Principal principal) {
        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now());
        }
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.findByUser(user));
        return "tasks/form";
    }



    @GetMapping("/filter")
    public String filterTasks(@RequestParam(required = false) Integer priority, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<Task> tasks;

        if (priority != null) {
            tasks = taskService.findByUserAndPriority(user, priority);
        } else {
            tasks = taskService.findByUser(user);
        }

        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/filterByCategory/{id}")
    public String filterTasksByCategory(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Category category = categoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));


        List<Task> tasks = taskService.findByUserAndCategory(user, category);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }



}
