package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findByUser(User user);


    List<Book> findByUserAndRating(User user, int rating);


    List<Book> findByUserAndGenre(User user, Genre genre);


    List<Book> findByGenre(Genre genre);
}
