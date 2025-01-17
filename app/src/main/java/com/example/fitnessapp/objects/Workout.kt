package com.example.fitnessapp.objects

import android.os.Parcel
import android.os.Parcelable

class Workout(var id: String?, var indexWorkout: Int ,@JvmField var workout: ArrayList<Exercise>, var name: String?, var day: String?,
    var email: String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.createTypedArrayList(Exercise.CREATOR) ?: arrayListOf(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()

    )

    constructor() : this("", -1, arrayListOf(), "", "", "")

    override fun toString(): String {
        return name.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(indexWorkout)
        parcel.writeTypedList(workout)
        parcel.writeString(name)
        parcel.writeString(day)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(parcel)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }

}
