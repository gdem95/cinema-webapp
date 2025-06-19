package com.cinema.dto;

public class PurchaseRequest {

    private Long showtimeId;
    private int quantity;
    
	public Long getShowtimeId() {
		return showtimeId;
	}
	
	public void setShowtimeId(Long showtimeId) {
		this.showtimeId = showtimeId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
