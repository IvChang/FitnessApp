package com.example.fitnessapp;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.fragments.OnItemInteractionListener;

public class AddExerciseHolder extends RecyclerView.ViewHolder {

    Button btn_addExercise;

    public AddExerciseHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        btn_addExercise = itemView.findViewById(R.id.btn_addExercise);

        btn_addExercise.setOnClickListener(v -> {
            listener.onAddExerciseButtonClick();
        });

    }
}
