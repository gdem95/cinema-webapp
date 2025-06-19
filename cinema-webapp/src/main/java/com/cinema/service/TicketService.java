package com.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.model.Showtime;
import com.cinema.model.Ticket;
import com.cinema.model.User;
import com.cinema.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    
    // Acquista un biglietto per lo spettacolo e l'utente specificato, impostando la quantità.
    public Ticket purchaseTicket(Showtime showtime, User user, int quantity) {
        Ticket ticket = new Ticket();
        ticket.setShowtime(showtime);
        ticket.setUser(user);
        ticket.setPurchaseTime(LocalDateTime.now());
        ticket.setQuantity(quantity);
        return ticketRepository.save(ticket);
    }
    
    // Restituisce una lista di biglietti acquistati da un determinato utente. 
    public List<Ticket> getTicketsByUser(User user) {
        return ticketRepository.findByUser(user);
    }
    
    // Restituisce la somma di biglietti per un determinato spettacolo.
    public int countTicketsForShowtime(Showtime showtime) {
        return ticketRepository
                .findAll()
                .stream()
                .filter(t -> t.getShowtime().equals(showtime))
                .mapToInt(Ticket::getQuantity)
                .sum();
    }
}



