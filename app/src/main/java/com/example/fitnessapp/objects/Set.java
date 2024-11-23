package com.example.fitnessapp.objects;

public class Set {


    private int reps, weight;
    private String variation;
    private boolean isVisible;

    public Set(int reps, int weight, String variation) {
        this.reps = reps;
        this.weight = weight;
        this.variation = variation;
        this.isVisible = true;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}
