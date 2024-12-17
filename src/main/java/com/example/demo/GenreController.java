package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping
    public String listGenres(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres/list"; // Ссылка на genres/list.html
    }

    // Отображение формы для добавления нового жанра
    @GetMapping("/new")
    public String newGenre(Model model) {
        model.addAttribute("genre", new Genre());
        return "genres/form";
    }

    // Сохранение нового жанра
    @PostMapping
    public String saveGenre(@ModelAttribute Genre genre, Model model) {
        try {
            genreService.saveGenre(genre);
            return "redirect:/genres";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("genre", genre);
            return "genres/form";
        }
    }

    // Просмотр одного жанра по ID
    @GetMapping("/{id}")
    public String showGenre(@PathVariable Long id, Model model) {
        Genre genre = genreService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id: " + id));

        model.addAttribute("genre", genre);
        return "genres/details"; // Ссылка на genres/details.html
    }
}
