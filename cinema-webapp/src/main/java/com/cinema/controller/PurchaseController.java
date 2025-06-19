package com.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cinema.model.Film;
import com.cinema.model.Showtime;
import com.cinema.model.Ticket;
import com.cinema.model.User;
import com.cinema.service.FilmService;
import com.cinema.service.ShowtimeService;
import com.cinema.service.TicketService;
import com.cinema.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PurchaseController {

    @Autowired
    private FilmService filmService;
    
    @Autowired
    private ShowtimeService showtimeService;
    
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private UserService userService;
    
    // Mostra la pagina di acquisto per un determinato film.
    @GetMapping("/purchase")
    public String purchasePage(@RequestParam("movie") Long filmId, Model model, Principal principal) {
        
    	if (principal == null) {
            return "redirect:/login";
        }
        
        Optional<Film> filmOpt = filmService.getFilmById(filmId);
        if (filmOpt.isEmpty()) {
            return "redirect:/home";
        }
        
        Film film = filmOpt.get();
        List<Showtime> futureShowtimes = showtimeService.getFutureShowtimesByFilm(film);
        model.addAttribute("film", film);
        model.addAttribute("showtimes", futureShowtimes);
        model.addAttribute("quantity", 1);
        return "purchase"; 
    }
    
    // Processa l'acquisto e crea un biglietto
    @PostMapping("/purchase")
    public String processPurchase(@RequestParam("showtimeId") Long showtimeId,
                                  @RequestParam("quantity") Integer quantity,
                                  Principal principal,
                                  Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        Optional<Showtime> showtimeOpt = showtimeService.getShowtimeById(showtimeId);
        if (showtimeOpt.isEmpty()) {
            return "redirect:/home";
        }
        
        Showtime showtime = showtimeOpt.get();
        if (!showtime.getShowDateTime().isAfter(java.time.LocalDateTime.now())) {
            return "redirect:/home";
        }
        
        User user = userService.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        
        int qty = (quantity != null && quantity >= 1) ? quantity : 1;
        Ticket ticket = ticketService.purchaseTicket(showtime, user, qty);
        
        return "redirect:/home";
    }
}



