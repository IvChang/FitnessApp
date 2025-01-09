package com.example.fitnessapp.holders

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.fragments.OnItemInteractionListener
import com.example.fitnessapp.objects.Set

class SetHolder(itemView: View, listener: OnItemInteractionListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var tv_setNo: TextView = itemView.findViewById(R.id.tv_setNo)
    var et_weight: EditText = itemView.findViewById(R.id.et_weight)
    var et_reps: EditText
    var iv_modifySet: ImageView
    var set: Set? = null
    var indexExercise = 0
    var isEditMode: Boolean = false
    var isModified: Boolean = false

    init {
        et_reps = itemView.findViewById(R.id.et_reps)
        iv_modifySet = itemView.findViewById(R.id.iv_modifySet)


        et_weight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!et_weight.text.toString().matches("".toRegex()) && !et_reps.text.toString()
                        .matches("".toRegex())
                ) {
                    val newWeight = et_weight.text.toString().toInt()
                    val newReps = et_reps.text.toString().toInt()
                    if (newWeight != set!!.weight || newReps != set!!.reps) {
                        iv_modifySet.visibility = View.VISIBLE
                        iv_modifySet.setImageResource(R.drawable.green_checkmark)
                        listener.onChangingSetStatus(
                            true,
                            indexExercise,
                            set!!.indexSet,
                            newWeight,
                            newReps
                        )
                    } else {
                        if (!isEditMode && !isModified) {
                            Log.d("heisenbug", "Heisenbug")
                            iv_modifySet.visibility = View.GONE
                        }
                        iv_modifySet.setImageResource(R.drawable.delete)
                        listener.onChangingSetStatus(
                            false,
                            indexExercise,
                            set!!.indexSet,
                            newWeight,
                            newReps
                        )
                    }
                } else {
                    iv_modifySet.setImageResource(R.drawable.delete)
                    if (!isEditMode && !isModified) {
                        iv_modifySet.visibility = View.GONE
                    }
                }
            }
        })

        et_reps.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!et_weight.text.toString().matches("".toRegex()) && !et_reps.text.toString()
                        .matches("".toRegex())
                ) {
                    val newWeight = et_weight.text.toString().toInt()
                    val newReps = et_reps.text.toString().toInt()
                    if (newWeight != set!!.weight || newReps != set!!.reps) {
                        iv_modifySet.visibility = View.VISIBLE
                        iv_modifySet.setImageResource(R.drawable.green_checkmark)
                        listener.onChangingSetStatus(
                            true,
                            indexExercise,
                            set!!.indexSet,
                            newWeight,
                            newReps
                        )
                    } else {
                        if (!isEditMode && !isModified) {

                            iv_modifySet.visibility = View.GONE
                        }
                        iv_modifySet.setImageResource(R.drawable.delete)
                        listener.onChangingSetStatus(
                            false,
                            indexExercise,
                            set!!.indexSet,
                            newWeight,
                            newReps
                        )
                    }
                } else {
                    iv_modifySet.setImageResource(R.drawable.delete)
                    if (!isEditMode && !isModified) {
                        iv_modifySet.visibility = View.GONE
                    }
                }
            }
        })

        iv_modifySet.setOnClickListener { v: View? ->
            Log.d("test1", "iv_modifySet called")
            if (isEditMode && !isModified) {
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setMessage("Are you sure to remove this set?")

                alertDialogBuilder.setPositiveButton(
                    "YES",
                    DialogInterface.OnClickListener { dialog, which ->
                        listener.onDeleteSetButtonClick(
                            adapterPosition, indexExercise, set!!.indexSet
                        )
                    })

                alertDialogBuilder.setNegativeButton(
                    "NO",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                        }
                    })
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            } else {
                listener.onModifySetButtonClick(
                    adapterPosition,
                    indexExercise,
                    set!!.indexSet,
                    et_weight.text.toString().toInt(),
                    et_reps.text.toString().toInt()
                )
                iv_modifySet.visibility = View.GONE
            }
        }
    }

    fun setSet(set: Set?, indexExercise: Int, isEditMode: Boolean) {
        this.set = set
        this.indexExercise = indexExercise
        this.isEditMode = isEditMode
        this.isModified = set!!.isModified

        if (isModified){
            iv_modifySet.visibility = View.VISIBLE
        }
    }

    fun updateImageView(deletionMode: Boolean) {
        if (deletionMode || set!!.isModified) {
            iv_modifySet.visibility = View.VISIBLE
        } else {
            if (!set!!.isModified) {
                iv_modifySet.visibility = View.GONE
            }
        }
        if (set!!.isModified) {
            iv_modifySet.setImageResource(R.drawable.green_checkmark)
        } else {
            iv_modifySet.setImageResource(R.drawable.delete)
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
