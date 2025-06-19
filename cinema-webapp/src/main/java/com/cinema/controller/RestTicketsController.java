package com.cinema.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cinema.dto.PurchaseRequest;
import com.cinema.dto.UserTicketDTO;
import com.cinema.model.Showtime;
import com.cinema.model.Ticket;
import com.cinema.model.User;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.repository.TicketRepository;
import com.cinema.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class RestTicketsController {

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    // Restituisce un JSON con lo storico dei biglietti acquistati.
    @GetMapping("/my-tickets")
    public ResponseEntity<?> getMyTickets(Authentication authentication) {
        try {
            String email = authentication.getName();

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato.");
            }

            List<Ticket> tickets = ticketRepository.findByUser_Email(email);

            if (tickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun biglietto acquistato.");
            }

            List<UserTicketDTO> ticketDTOs = tickets.stream().map(ticket -> {
                UserTicketDTO dto = new UserTicketDTO();
                dto.setTicketId(ticket.getId());
                dto.setShowtimeId(ticket.getShowtime().getId());
                dto.setFilmTitle(ticket.getShowtime().getFilm().getTitle());
                dto.setShowtime(ticket.getShowtime().getShowDateTime());
                dto.setQuantity(ticket.getQuantity());
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(ticketDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore del server.");
        }
    }
	
    // Gestisce l'acquisto dei biglietti via API.
    @PostMapping("/purchase")
    public ResponseEntity<String> buyTickets(@RequestBody PurchaseRequest request,
                                                          Authentication authentication) {

        try {
            String email = authentication.getName();

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato.");
            }

            Optional<Showtime> optionalShowtime = showtimeRepository.findById(request.getShowtimeId());
            if (optionalShowtime.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spettacolo non trovato.");
            }

            if (request.getQuantity() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantità non valida.");
            }

            Showtime showtime = optionalShowtime.get();
            if (showtime.getShowDateTime().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attenzione! Lo spettacolo richiesto è già passato.");
            }

            Ticket ticket = new Ticket();
            ticket.setUser(optionalUser.get());
            ticket.setShowtime(showtime);
            ticket.setQuantity(request.getQuantity());
            ticket.setPurchaseTime(LocalDateTime.now());

            ticketRepository.save(ticket);

            return ResponseEntity.ok("Biglietto acquistato con successo!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore del server.");
        }
    }
    
}
