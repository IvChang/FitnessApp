package com.example.fitnessapp.objects;

import java.util.ArrayList;

public class Workout {

    private ArrayList<Exercise> workout;
    private String name, day;


    public Workout(ArrayList<Exercise> workout, String name, String day) {
        this.workout = workout;
        this.name = name;
        this.day = day;
    }

    public ArrayList<Exercise> getWorkout() {
        return workout;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public void setWorkout(ArrayList<Exercise> workout) {
        this.workout = workout;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
