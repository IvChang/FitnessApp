package com.example.fitnessapp;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.fragments.OnItemInteractionListener;

public class AddSetHolder extends RecyclerView.ViewHolder{

    Button btn_addSet;
    private int indexExercise;

    public AddSetHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        btn_addSet = itemView.findViewById(R.id.btn_addSet);

        btn_addSet.setOnClickListener(v -> {
            listener.onAddSetButtonClick(getAdapterPosition(), indexExercise);
        });

    }

    public void setIndexExercise(int indexExercise) {
        this.indexExercise = indexExercise;
    }

}
