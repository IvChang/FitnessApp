package com.example.fitnessapp.objects

import android.os.Parcel
import android.os.Parcelable

class Set(@JvmField var reps: Int, @JvmField var weight: Int, var variation: String, @JvmField var indexSet: Int) :
    Parcelable {
    @JvmField
    var newReps: Int = reps
    @JvmField
    var newWeight: Int = weight
    @JvmField
    var isVisible: Boolean = true
    @JvmField
    var isModified: Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
        newReps = parcel.readInt()
        newWeight = parcel.readInt()
        isVisible = parcel.readByte() != 0.toByte()
        isModified = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(reps)
        parcel.writeInt(weight)
        parcel.writeString(variation)
        parcel.writeInt(indexSet)
        parcel.writeInt(newReps)
        parcel.writeInt(newWeight)
        parcel.writeByte(if (isVisible) 1 else 0)
        parcel.writeByte(if (isModified) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Set> {
        override fun createFromParcel(parcel: Parcel): Set {
            return Set(parcel)
        }

        override fun newArray(size: Int): Array<Set?> {
            return arrayOfNulls(size)
        }
    }
}
