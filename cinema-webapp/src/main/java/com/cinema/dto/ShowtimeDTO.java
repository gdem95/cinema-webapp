package com.cinema.dto;

import java.time.LocalDateTime;

public class ShowtimeDTO {

    private Long id;
    private Long filmId;
    private String filmTitle;
    private LocalDateTime showtime;
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFilmId() {
		return filmId;
	}
	
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}
	
	public String getFilmTitle() {
		return filmTitle;
	}
	
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	
	public LocalDateTime getShowtime() {
		return showtime;
	}
	
	public void setShowtime(LocalDateTime showtime) {
		this.showtime = showtime;
	}
	
}
