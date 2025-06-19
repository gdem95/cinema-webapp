package com.cinema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "showtimes")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    
    @Column(nullable = false)
    private LocalDateTime showDateTime;

    public Showtime() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Film getFilm() {
        return film;
    }
    public void setFilm(Film film) {
        this.film = film;
    }
    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }
    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }
}

