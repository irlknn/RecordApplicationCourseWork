package models;

import java.time.LocalTime;

public class Record {
    private int id;
    private String title;
    private String style;
    private LocalTime duration;

    public Record(String title, String style, LocalTime duration){
        this.title = title;
        this.style = style;
        this.duration = duration;
    }

    public Record() {}

    public int getId(){return id;}
    public String getTitle() {return title;}
    public String getStyle() {return this.style;}
    public LocalTime getDuration() {return duration;}

    public void setId(int id){this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setStyle(String style) {this.style = style;}
    public void setDuration(LocalTime duration) {this.duration = duration;}

    @Override
    public String toString() {
        return "Title: " + title + " Style: " + style + " Duration: " + duration;
    }

}
