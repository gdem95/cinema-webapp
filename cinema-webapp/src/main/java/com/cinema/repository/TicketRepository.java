package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.model.Film;
import com.cinema.model.Ticket;
import com.cinema.model.User;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    List<Ticket> findByUser(User user);
    List<Ticket> findByUser_Email(String email);
    
}
