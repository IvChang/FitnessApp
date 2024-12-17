package com.example.fitnessapp.fragments;

public interface OnItemInteractionListener {
    void onToggleButtonClick(int position, int idExercise);
    void onAddSetButtonClick(int position, int indexExercise);
}
