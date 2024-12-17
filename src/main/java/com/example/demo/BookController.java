package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenreService genreService;


    @GetMapping
    public String listBooks(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<Book> books = bookService.findByUser(user);
        model.addAttribute("books", books);
        return "books/list";
    }


    @GetMapping("/list")
    public String listAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }


    @GetMapping("/new")
    public String newBook(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genres);
        return "books/form";
    }

    @PostMapping
    public String saveBook(@ModelAttribute Book book, @RequestParam("genreId") Long genreId, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Genre genre = genreService.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found"));

        if (book.getRating() == null) {
            book.setRating(1);
        }
        if (book.getDueDate() == null) {
            book.setDueDate(LocalDate.now());
        }

        book.setUser(user);
        book.setGenre(genre);

        bookService.save(book);
        return "redirect:/books";
    }


    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }


    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model, Principal principal) {
        Book book = bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        if (book.getDueDate() == null) {
            book.setDueDate(LocalDate.now());
        }
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        return "books/form";
    }


    @GetMapping("/filter")
    public String filterBooks(@RequestParam(required = false) Integer rating, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<Book> books;

        if (rating != null) {
            books = bookService.findByUserAndRating(user, rating);
        } else {
            books = bookService.findByUser(user);
        }

        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/filterByGenre/{id}")
    public String filterBooksByGenre(@PathVariable Long id, Model model) {
        Genre genre = genreService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + id));

        List<Book> books = bookService.findByGenre(genre);
        model.addAttribute("books", books);
        model.addAttribute("genres", genreService.findAll());
        return "books/list"; // Путь к шаблону
    }

}
