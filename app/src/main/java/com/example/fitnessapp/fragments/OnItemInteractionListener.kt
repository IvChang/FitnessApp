package com.example.fitnessapp.fragments

interface OnItemInteractionListener {
    fun onToggleButtonClick(position: Int, idExercise: Int)
    fun onAddSetButtonClick(position: Int, indexExercise: Int)
    fun onModifyExerciseButtonClick(position: Int, indexExercise: Int, name: String?, note: String?)
    fun onModifySetButtonClick(
        position: Int,
        indexExercise: Int,
        indexSet: Int,
        weight: Int,
        reps: Int
    )

    fun onModifySetModeButtonClick(deletionMode: Boolean, position: Int, indexExercise: Int)
    fun onChangingSetStatus(
        isModified: Boolean,
        indexExercise: Int,
        indexSet: Int,
        newWeight: Int,
        newReps: Int
    )

    fun onDeleteSetButtonClick(position: Int, indexExercise: Int, indexSet: Int)
    fun onDeleteExerciseButtonClick(position: Int, indexExercise: Int)
    fun onAddExerciseButtonClick(position: Int)
}
