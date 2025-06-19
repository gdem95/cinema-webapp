package com.cinema.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.dto.FilmWithShowtimesDTO;
import com.cinema.dto.ShowtimeDTO;
import com.cinema.model.Film;
import com.cinema.model.Showtime;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.service.FilmService;
import com.cinema.service.ShowtimeService;


@RestController
@RequestMapping("/api")
public class RestFilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
	private ShowtimeRepository showtimeRepository;

    // Restituisce la lista dei film in programmazione.
    @GetMapping("/films")
    public List<FilmWithShowtimesDTO> getFilmInProgrammazione() {
        List<Film> films = filmService.getAllFilms();

        return films.stream().map(film -> {
            List<Showtime> futureShowtimes = showtimeService.getFutureShowtimesByFilm(film);

            if (futureShowtimes.isEmpty()) return null;

            FilmWithShowtimesDTO dto = new FilmWithShowtimesDTO();
            dto.setFilmId(film.getId());
            dto.setTitle(film.getTitle());
            dto.setYear(film.getYear());
            dto.setDuration(film.getDuration());
            dto.setDescription(film.getDescription());

            List<LocalDateTime> showtimes = futureShowtimes.stream()
                .map(Showtime::getShowDateTime)
                .collect(Collectors.toList());

            dto.setShowtimes(showtimes);

            return dto;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }
    
    // Restituisce la lista degli spettacoli.
    @GetMapping("/showtimes")
    public List<ShowtimeDTO> getAllShowtimes() {
        List<Showtime> showtimes = showtimeRepository.findByShowDateTimeAfter(LocalDateTime.now());

        return showtimes.stream()
                .map(s -> {
                    ShowtimeDTO dto = new ShowtimeDTO();
                    dto.setId(s.getId()); 
                    dto.setFilmId(s.getFilm().getId()); 
                    dto.setFilmTitle(s.getFilm().getTitle()); 
                    dto.setShowtime(s.getShowDateTime()); 
                    return dto;
                })
                .collect(Collectors.toList()); 
    }
	
}
