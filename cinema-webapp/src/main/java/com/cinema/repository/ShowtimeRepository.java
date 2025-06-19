package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.model.Film;
import com.cinema.model.Showtime;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    
	// Recupera gli spettacoli di un film con data/ora futura.
    List<Showtime> findByFilmAndShowDateTimeAfter(Film film, LocalDateTime dateTime);

    // Recupera tutti gli spettacoli con data/ora futura.
    List<Showtime> findByShowDateTimeAfter(LocalDateTime now);
    
    // Recupera tutti gli spettacoli per un determinato film.
    List<Showtime> findByFilm(Film film);

}

