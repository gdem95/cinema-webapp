package com.cinema.model;

import java.util.Base64;

import jakarta.persistence.*;

@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] poster;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private int year;
    
    @Column(nullable = false)
    private int duration; 
    
    @Column(length = 1000)
    private String description;

    public Film() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public byte[] getPoster() {
        return poster;
    }
    public void setPoster(byte[] poster) {
        this.poster = poster;
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
    
    @Transient
    public String getPosterBase64() {
        if (this.poster != null) {
            return Base64.getEncoder().encodeToString(this.poster);
        }
        return "";
    }
}



