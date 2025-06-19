package com.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cinema.model.User;
import com.cinema.model.Ticket;
import com.cinema.service.TicketService;
import com.cinema.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class MyTicketsController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private UserService userService;
    
    // Mostra lo storico dei biglietti acquistati.
    @GetMapping("/my-tickets")
    public String myTicketsPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        User user = userService.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Ticket> tickets = ticketService.getTicketsByUser(user);
        model.addAttribute("tickets", tickets);
        return "my-tickets";
    }
}




