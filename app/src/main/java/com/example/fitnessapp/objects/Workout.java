package com.example.fitnessapp.objects;

public class Workout {

    private ExerciseGroup[] workout;
    private String name, day;


    public Workout(ExerciseGroup[] workout, String name, String day) {
        this.workout = workout;
        this.name = name;
        this.day = day;
    }

    public ExerciseGroup[] getWorkout() {
        return workout;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public void setWorkout(ExerciseGroup[] workout) {
        this.workout = workout;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
