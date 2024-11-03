package com.example.fitnessapp.objects;

import java.util.ArrayList;

public class ExerciseGroup {

    private ArrayList<Exercise> group;


    public ExerciseGroup(ArrayList<Exercise> group) {
        this.group = group;
    }

    public ArrayList<Exercise> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Exercise> group) {
        this.group = group;
    }
}
