package com.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.model.Film;
import com.cinema.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }
    
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }
    
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
    
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}

