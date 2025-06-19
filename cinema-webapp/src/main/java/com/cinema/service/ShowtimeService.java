package com.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.model.Film;
import com.cinema.model.Showtime;
import com.cinema.repository.ShowtimeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;
    
    public List<Showtime> getShowtimesByFilm(Film film) {
        return showtimeRepository.findByFilm(film);
    }
    
    public List<Showtime> getFutureShowtimesByFilm(Film film) {
        return showtimeRepository.findByFilmAndShowDateTimeAfter(film, LocalDateTime.now());
    }
    
    public Optional<Showtime> getShowtimeById(Long id) {
        return showtimeRepository.findById(id);
    }
    
    public Showtime saveShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }
    
    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }
}

