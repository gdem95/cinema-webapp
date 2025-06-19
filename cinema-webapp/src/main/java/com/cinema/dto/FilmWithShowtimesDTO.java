package com.cinema.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FilmWithShowtimesDTO {

    private Long filmId;
    private String title;
    private int year;
    private int duration;
    private String description;

    private List<LocalDateTime> showtimes;
	public Long getFilmId() {
		return filmId;
	}
	
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<LocalDateTime> getShowtimes() {
		return showtimes;
	}
	
	public void setShowtimes(List<LocalDateTime> showtimes) {
		this.showtimes = showtimes;
	}
	
}
