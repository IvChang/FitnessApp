package com.example.fitnessapp.objects;

import java.util.ArrayList;

public class Exercise {

    private int id, indexExercise;
    private String name, type, note;
    private boolean setsAreVisible;
    private ArrayList<Set> sets;

    public Exercise(int id, String name, String type, String note, ArrayList<Set> sets, boolean setsAreVisible,
                    int indexExercise) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.note = note;
        this.sets = sets;
        this.setsAreVisible = setsAreVisible;
        this.indexExercise = indexExercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean getSetsAreVisible() {
        return setsAreVisible;
    }

    public void setSetsAreVisible(boolean setsAreVisible) {
        this.setsAreVisible = setsAreVisible;
    }

    public int getIndexExercise() {
        return indexExercise;
    }

    public void setIndexExercise(int indexExercise) {
        this.indexExercise = indexExercise;
    }
}
