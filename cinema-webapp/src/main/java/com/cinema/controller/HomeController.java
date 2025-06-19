package com.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cinema.model.Film;
import com.cinema.service.FilmService;
import com.cinema.service.ShowtimeService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private FilmService filmService;
    
    @Autowired
    private ShowtimeService showtimeService;
    
    // Mostra la homepage con i film in programmazione.
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        
        List<Film> allFilms = filmService.getAllFilms();
        
        List<Film> filmsWithFutureShowtimes = allFilms.stream()
                .filter(film -> !showtimeService.getFutureShowtimesByFilm(film).isEmpty())
                .collect(Collectors.toList());
                
        model.addAttribute("films", filmsWithFutureShowtimes);
        return "home";
    }
}


