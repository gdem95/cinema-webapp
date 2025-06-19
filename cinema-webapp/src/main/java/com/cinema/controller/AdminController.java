package com.cinema.controller;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cinema.model.Film;
import com.cinema.model.Showtime;
import com.cinema.service.FilmService;
import com.cinema.service.ShowtimeService;
import com.cinema.service.TicketService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FilmService filmService;
    
    @Autowired
    private ShowtimeService showtimeService;
    
    @Autowired
    private TicketService ticketService;
    
    // Pagina admin: elenco dei film
    @GetMapping
    public String adminDashboard(Model model) {
        List<Film> films = filmService.getAllFilms();
        model.addAttribute("films", films);
        return "admin";
    }
    
    // Nuovo film: mostra il form per aggiungere un nuovo film
    @GetMapping("/film/new")
    public String newFilmForm(Model model) {
        model.addAttribute("film", new Film());
        return "admin-film-form";
    }
    
    // Salvataggio nuovo film
    @PostMapping("/film/save")
    public String saveFilm(@ModelAttribute Film film,
                           @RequestParam("posterFile") MultipartFile posterFile,
                           Model model) {

        film.setTitle(TextSanitizer.sanitize(film.getTitle()));
        film.setDescription(TextSanitizer.sanitize(film.getDescription()));
    	
        if (film.getTitle() == null || film.getTitle().trim().isEmpty()) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "È necessario specificare il titolo.");
            return "admin-film-form";
        }
        if (film.getYear() <= 1895 || film.getYear() > java.time.Year.now().getValue()) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "L'anno di uscita non è valido.");
            return "admin-film-form";
        }
        if (film.getDuration() <= 0) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "È necessario inserire la durata (in minuti) del film.");
            return "admin-film-form";
        }
        if (film.getDescription() == null || film.getDescription().trim().isEmpty()) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "È necessario fornire una sinossi.");
            return "admin-film-form";
        }
        if (posterFile.isEmpty()) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "È necessario caricare una locandina.");
            return "admin-film-form";
        }
        if (posterFile.getSize() > 5 * 1024 * 1024) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "L'immagine supera i 5 MB.");
            return "admin-film-form";
        }
        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(posterFile.getInputStream());

            if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png")) {
            	model.addAttribute("film", film);
                model.addAttribute("error", "È consentito solo l'upload di immagini JPEG o PNG.");
                return "admin-film-form";
            }
        } catch (IOException e) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "Errore nel verificare il MIME Type.");
            return "admin-film-form";
        }
        
        try {
            byte[] bytes = posterFile.getBytes();
            film.setPoster(bytes);
            filmService.saveFilm(film);
            return "redirect:/admin";
        } catch (IOException e) {
        	model.addAttribute("film", film);
            model.addAttribute("error", "Errore nel salvataggio del film.");
            return "redirect:/admin-film-form";
        }
    }
    
    // Elimina un film
    @GetMapping("/film/delete/{id}")
    public String deleteFilm(@PathVariable("id") Long filmId) {
        filmService.deleteFilm(filmId);
        return "redirect:/admin";
    }
    
    // Lista degli spettacoli
    @GetMapping("/film/{id}/showtimes")
    public String manageShowtimes(@PathVariable("id") Long filmId, Model model) {
        Optional<Film> filmOpt = filmService.getFilmById(filmId);
        if (filmOpt.isEmpty()) {
            return "redirect:/admin?error=filmNotFound";
        }
        Film film = filmOpt.get();
        List<Showtime> showtimes = showtimeService.getShowtimesByFilm(film);
        Map<Long, Integer> ticketCount = showtimes.stream()
                .collect(Collectors.toMap(Showtime::getId,
                                         st -> ticketService.countTicketsForShowtime(st)));
        
        model.addAttribute("film", film);
        model.addAttribute("showtimes", showtimes);
        model.addAttribute("ticketCount", ticketCount);
        return "admin-showtimes";
    }
    
    // Mostra il form per aggiungere un nuovo spettacolo per un film
    @GetMapping("/showtime/new")
    public String newShowtimeForm(@RequestParam("film") Long filmId, Model model) {
        Optional<Film> filmOpt = filmService.getFilmById(filmId);
        if (filmOpt.isEmpty()) {
            return "redirect:/admin?error=filmNotFound";
        }
        Showtime showtime = new Showtime();
        showtime.setFilm(filmOpt.get());
        model.addAttribute("showtime", showtime);
        return "admin-showtime-form";
    }
    
    // Salva lo spettacolo
    @PostMapping("/showtime/save")
    public String saveShowtime(@ModelAttribute Showtime showtime,
                               @RequestParam("showDateTimeStr") String showDateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        showtime.setShowDateTime(LocalDateTime.parse(showDateTimeStr, formatter));
        showtimeService.saveShowtime(showtime);
        return "redirect:/admin/film/" + showtime.getFilm().getId() + "/showtimes";
    }
    
    // Elimina uno spettacolo
    @GetMapping("/showtime/delete/{id}")
    public String deleteShowtime(@PathVariable("id") Long showtimeId) {
        Optional<Showtime> showtimeOpt = showtimeService.getShowtimeById(showtimeId);
        if (showtimeOpt.isPresent()) {
            Long filmId = showtimeOpt.get().getFilm().getId();
            showtimeService.deleteShowtime(showtimeId);
            return "redirect:/admin/film/" + filmId + "/showtimes";
        }
        return "redirect:/admin";
    }
}

