package com.example.fitnessapp

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.fragments.OnItemInteractionListener

class AddSetHolder(itemView: View, listener: OnItemInteractionListener) :
    RecyclerView.ViewHolder(itemView) {
    var btn_addSet: Button = itemView.findViewById(R.id.btn_addSet)
    private var indexExercise = 0

    init {
        btn_addSet.setOnClickListener { v: View? ->
            listener.onAddSetButtonClick(
                adapterPosition, indexExercise
            )
        }
    }

    fun setIndexExercise(indexExercise: Int) {
        this.indexExercise = indexExercise
    }
}
