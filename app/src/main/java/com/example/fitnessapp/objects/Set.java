package com.example.fitnessapp.objects;

public class Set {


    private int reps, weight, indexSet, newReps, newWeight;
    private String variation;
    private boolean isVisible, isModified;

    public Set(int reps, int weight, String variation, int indexSet) {
        this.reps = reps;
        this.weight = weight;
        this.variation = variation;
        this.isVisible = true;
        this.indexSet = indexSet;
        this.isModified = false;
        this.newReps = reps;
        this.newWeight = weight;
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

    public int getIndexSet() {
        return indexSet;
    }

    public void setIndexSet(int indexSet) {
        this.indexSet = indexSet;
    }

    public boolean getIsModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }

    public int getNewReps() {
        return newReps;
    }

    public void setNewReps(int newReps) {
        this.newReps = newReps;
    }

    public int getNewWeight() {
        return newWeight;
    }

    public void setNewWeight(int newWeight) {
        this.newWeight = newWeight;
    }

}
