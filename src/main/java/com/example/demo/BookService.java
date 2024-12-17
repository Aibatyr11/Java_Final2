package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book save(Book book) {
        return bookRepository.save(book);
    }


    public List<Book> findByUser(User user) {
        return bookRepository.findByUser(user);
    }


    public void delete(Long id) {
        bookRepository.deleteById(id);
    }


    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }


    public List<Book> findByUserAndRating(User user, int rating) {
        return bookRepository.findByUserAndRating(user, rating);
    }


    public List<Book> findByUserAndGenre(User user, Genre genre) {
        return bookRepository.findByUserAndGenre(user, genre);
    }


    public List<Book> findByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }


    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
