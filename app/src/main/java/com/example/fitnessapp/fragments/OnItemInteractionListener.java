package com.example.fitnessapp.fragments;

public interface OnItemInteractionListener {
    void onToggleButtonClick(int position, int idExercise);
    void onAddSetButtonClick(int position, int indexExercise);
    void onModifyExerciseButtonClick(int position, int indexExercise, String name, String note);
    void onModifySetButtonClick(int position, int indexExercise, int indexSet, int weight, int reps);
}
