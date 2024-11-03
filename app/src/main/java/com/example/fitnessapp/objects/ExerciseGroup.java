package com.example.fitnessapp.objects;

public class ExerciseGroup {

    private Exercise[] group;


    public ExerciseGroup(Exercise[] group) {
        this.group = group;
    }

    public Exercise[] getGroup() {
        return group;
    }

    public void setGroup(Exercise[] group) {
        this.group = group;
    }
}
