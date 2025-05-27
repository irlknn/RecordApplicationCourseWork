package org.example.models;

public class Collection {
    private int id;
    private String name;

    public Collection(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){return this.name;}
}
