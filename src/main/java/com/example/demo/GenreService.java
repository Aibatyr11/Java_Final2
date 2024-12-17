package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void saveGenre(Genre genre) {
        if (genreRepository.findByGenreName(genre.getGenreName()).isPresent()) {
            throw new IllegalArgumentException("Genre name already exists");
        }
        genreRepository.save(genre);
    }


    public List<Genre> findAll() {
        return genreRepository.findAll();
    }


    public Optional<Genre> findByName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }
}
