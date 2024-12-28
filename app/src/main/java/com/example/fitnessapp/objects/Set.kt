package com.example.fitnessapp.objects

class Set(@JvmField var reps: Int, @JvmField var weight: Int, var variation: String, @JvmField var indexSet: Int) {
    @JvmField
    var newReps: Int = reps
    @JvmField
    var newWeight: Int = weight
    @JvmField
    var isVisible: Boolean = true
    @JvmField
    var isModified: Boolean = false
}
