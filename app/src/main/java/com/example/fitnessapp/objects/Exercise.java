package com.example.fitnessapp.objects;

import java.util.ArrayList;

public class Exercise {

    private String name, type, note;
    private ArrayList<Set> sets;

    public Exercise(String name, String type, String note, ArrayList<Set> sets) {
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

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }
}
