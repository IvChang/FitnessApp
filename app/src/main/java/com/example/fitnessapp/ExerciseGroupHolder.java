package com.example.fitnessapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseGroupHolder extends RecyclerView.ViewHolder{

    TextView tv_name, tv_sets;

    public ExerciseGroupHolder(@NonNull View itemView) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.tv_name);
        tv_sets = itemView.findViewById(R.id.tv_sets);
    }
}
