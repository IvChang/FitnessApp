package com.example.fitnessapp.objects

class Workout(@JvmField var workout: ArrayList<Exercise>, var name: String, var day: String) {
    override fun toString(): String {
        return name
    }

}
