package org.example.models;

import java.time.LocalTime;

public class Record {
    private int id;
    private String title;
    private String style;
    private LocalTime duration;
    private String author;
    private String description;

//    public Record(String title, String style, LocalTime duration) {
//        this.title = title;
//        this.style = style;
//        this.duration = duration;
//    }

    public Record(String title, String style, LocalTime duration, String author, String description) {
        this.title = title;
        this.style = style;
        this.duration = duration;
        this.author = author;
        this.description = description;
    }


    public Record() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getAuthor() {return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Title: " + title + " Style: " + style + " Duration: " + duration + " Author: " + author + " Description: " + description;
    }

}
