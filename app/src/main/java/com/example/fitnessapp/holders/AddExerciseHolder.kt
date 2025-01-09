package com.example.fitnessapp.holders

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.fragments.OnItemInteractionListener

class AddExerciseHolder(itemView: View, listener: OnItemInteractionListener) :
    RecyclerView.ViewHolder(itemView) {
    var btn_addExercise: Button = itemView.findViewById(R.id.btn_addExercise)

    init {
        btn_addExercise.setOnClickListener { v: View? ->
            listener.onAddExerciseButtonClick(adapterPosition);
        }
    }
}
