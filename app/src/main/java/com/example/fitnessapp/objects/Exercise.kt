package com.example.fitnessapp.objects

class Exercise(
    @JvmField var id: Int,
    @JvmField var name: String,
    @JvmField var type: String,
    @JvmField var note: String,
    @JvmField var sets: ArrayList<Set>,
    @JvmField var setsAreVisible: Boolean,
    @JvmField var indexExercise: Int,
    @JvmField var isEditMode: Boolean
)
