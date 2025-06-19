package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.model.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {

}
