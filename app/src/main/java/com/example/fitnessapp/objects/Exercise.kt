package com.example.fitnessapp.objects

import android.os.Parcel
import android.os.Parcelable

class Exercise(
    @JvmField var id: Int,
    @JvmField var name: String,
    @JvmField var type: String,
    @JvmField var note: String,
    @JvmField var sets: ArrayList<Set>,
    @JvmField var setsAreVisible: Boolean,
    @JvmField var indexExercise: Int,
    @JvmField var isEditMode: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createTypedArrayList(Set.CREATOR) ?: arrayListOf(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    constructor() : this(-1, "", "", "", arrayListOf(), false, -1, false)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(note)
        parcel.writeTypedList(sets)
        parcel.writeByte(if (setsAreVisible) 1 else 0)
        parcel.writeInt(indexExercise)
        parcel.writeByte(if (isEditMode) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}
