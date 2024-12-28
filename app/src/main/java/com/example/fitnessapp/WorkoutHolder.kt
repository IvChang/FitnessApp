package com.example.fitnessapp

import android.app.AlertDialog
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.fragments.OnItemInteractionListener
import com.example.fitnessapp.objects.Exercise

class WorkoutHolder(itemView: View, private val listener: OnItemInteractionListener) :
    RecyclerView.ViewHolder(itemView), PopupMenu.OnMenuItemClickListener {
    var tv_sets: TextView = itemView.findViewById(R.id.tv_sets)
    var et_note: EditText = itemView.findViewById(R.id.et_note)
    var actv_name: AutoCompleteTextView = itemView.findViewById(R.id.actv_name)
    var iv_toggleSets: ImageView = itemView.findViewById(R.id.iv_toggleSets)
    var iv_exerciseOptions: ImageView = itemView.findViewById(R.id.iv_exerciseOptions)
    private var exercise: Exercise? = null
    private var indexExercise = 0
    private var inEditMode = false
    var exerciseList: Array<String> = arrayOf("Push-Ups", "Pull-Ups", "Deadlift", "Inverted Row", "Squat", "Lunge", "Pistol Squat",
        "Lateral Raise", "Overhead Press", "Bench Press", "Dumbbell Curl", "Dips", "Hip Thrust", "Chin-Ups", "Dumbbell Row",
        "Nordic Curl", "Reverse Nordic Curl", "Overhead Tricep Extension", "Hammer Curl", "Romanian Deadlift")

    init {

        val adapter =
            ArrayAdapter(itemView.context, android.R.layout.simple_list_item_1, exerciseList)
        actv_name.setAdapter(adapter)


        // listener pour cacher/dévoiler les sets
        iv_toggleSets.setOnClickListener { v: View? ->
            listener.onToggleButtonClick(adapterPosition, this.indexExercise)
            iv_toggleSets.rotation = iv_toggleSets.rotation - 180
        }

        // listener pour afficher le menu popup de l'exercice
        iv_exerciseOptions.setOnClickListener { v: View? ->
            if (inEditMode) {
                listener.onModifyExerciseButtonClick(
                    adapterPosition, this.indexExercise, actv_name.text.toString(),
                    et_note.text.toString()
                )

                iv_exerciseOptions.setImageResource(R.drawable.three_dots)
                inEditMode = false
                actv_name.isClickable = false
                actv_name.isFocusable = false
                actv_name.isFocusableInTouchMode = false
                et_note.isClickable = false
                et_note.isFocusable = false
                et_note.isFocusableInTouchMode = false
                listener.onModifySetModeButtonClick(false, adapterPosition, indexExercise)
            } else {
                val popupMenu = PopupMenu(itemView.context, iv_exerciseOptions)
                popupMenu.setOnMenuItemClickListener(this)
                val inflater = popupMenu.menuInflater
                inflater.inflate(R.menu.exercisemenu, popupMenu.menu)
                popupMenu.show()
            }
        }
    }

    fun bindWorkout(exercise: Exercise?) {
        this.exercise = exercise
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.pop_mnu_modify) {
            Log.d("test1", "modify")

            actv_name.isClickable = true
            actv_name.isFocusable = true
            actv_name.isFocusableInTouchMode = true
            et_note.isClickable = true
            et_note.isFocusable = true
            et_note.isFocusableInTouchMode = true
            actv_name.requestFocus()
            iv_exerciseOptions.setImageResource(R.drawable.green_checkmark)
            inEditMode = true
            Log.d("test1", "indexExercise : $indexExercise")
            listener.onModifySetModeButtonClick(true, adapterPosition, indexExercise)
        } else if (item.itemId == R.id.pop_mnu_remove) {
            Log.d("test1", "remove")
            val alertDialogBuilder = AlertDialog.Builder(itemView.context)
            alertDialogBuilder.setMessage("Are you sure to remove this exercise?")

            alertDialogBuilder.setPositiveButton("YES") { dialog, which ->
                listener.onDeleteExerciseButtonClick(
                    adapterPosition, indexExercise
                )
            }

            alertDialogBuilder.setNegativeButton("NO") { dialog, which -> }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        return false
    }

    fun setIndexExercise(indexExercise: Int) {
        this.indexExercise = indexExercise
    }

}
