package com.example.bookfortune;

public class BookDto {
    private String title;
    private String author;
    private String description;

    public BookDto(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
}