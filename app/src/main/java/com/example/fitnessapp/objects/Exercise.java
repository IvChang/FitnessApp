package com.example.fitnessapp.objects;

public class Exercise {

    private String name, type, note;
    private Set[] sets;

    public Exercise(String name, String type, String note, Set[] sets) {
        this.name = name;
        this.type = type;
        this.note = note;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set[] getSets() {
        return sets;
    }

    public void setSets(Set[] sets) {
        this.sets = sets;
    }
}
