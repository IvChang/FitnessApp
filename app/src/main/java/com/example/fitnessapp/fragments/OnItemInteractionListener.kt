package com.example.fitnessapp.fragments;

public interface OnItemInteractionListener {
    void onToggleButtonClick(int position, int idExercise);
    void onAddSetButtonClick(int position, int indexExercise);
    void onModifyExerciseButtonClick(int position, int indexExercise, String name, String note);
    void onModifySetButtonClick(int position, int indexExercise, int indexSet, int weight, int reps);
    void onModifySetModeButtonClick(boolean deletionMode, int position, int indexExercise);
    void onChangingSetStatus(String status, int indexExercise, int indexSet, int newWeight, int newReps);
    void onDeleteSetButtonClick(int position, int indexExercise, int indexSet);
    void onDeleteExerciseButtonClick(int position, int indexExercise);
    void onAddExerciseButtonClick();
}
