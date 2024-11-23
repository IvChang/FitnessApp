package com.example.fitnessapp;

import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.objects.Workout;
import com.example.fitnessapp.fragments.OnItemInteractionListener;

public class WorkoutHolder extends RecyclerView.ViewHolder{

    TextView tv_name, tv_sets;
    ImageView iv_toggleSets;
    private Exercise exercise;


    public WorkoutHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.tv_name);
        tv_sets = itemView.findViewById(R.id.tv_sets);
        iv_toggleSets = itemView.findViewById(R.id.iv_toggleSets);



        iv_toggleSets.setOnClickListener(v -> {
            listener.onModifyButtonClick(getAdapterPosition(), tv_name.getText().toString());
            iv_toggleSets.setRotation(iv_toggleSets.getRotation() - 180);
        });

    }

    public void bindWorkout(Exercise exercise) {
        this.exercise = exercise;
    }

}
