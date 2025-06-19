package com.cinema.dto;

import java.time.LocalDateTime;

public class UserTicketDTO {
	
    private Long ticketId;
    private Long showtimeId;
    private String filmTitle;
    private LocalDateTime showtime;
    private int quantity;
    
	public Long getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	
	public Long getShowtimeId() {
		return showtimeId;
	}
	
	public void setShowtimeId(Long showtimeId) {
		this.showtimeId = showtimeId;
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
