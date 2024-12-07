package com.example.fitnessapp;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.fragments.OnItemInteractionListener;

public class WorkoutHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    TextView tv_name, tv_sets;
    ImageView iv_toggleSets, iv_exerciseOptions;
    private Exercise exercise;
    private int idExercise;



    public WorkoutHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);



        tv_name = itemView.findViewById(R.id.tv_name);
        tv_sets = itemView.findViewById(R.id.tv_sets);
        iv_toggleSets = itemView.findViewById(R.id.iv_toggleSets);
        iv_exerciseOptions = itemView.findViewById(R.id.iv_exerciseOptions);


        // listener pour cacher/dévoiler les sets
        iv_toggleSets.setOnClickListener(v -> {
            listener.onToggleButtonClick(getAdapterPosition(), this.idExercise);
            iv_toggleSets.setRotation(iv_toggleSets.getRotation() - 180);
        });

        // listener pour afficher le menu popup de l'exercice
        iv_exerciseOptions.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), iv_exerciseOptions);
            popupMenu.setOnMenuItemClickListener(this);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.exercisemenu, popupMenu.getMenu());
            popupMenu.show();
        });

    }

    public void bindWorkout(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
        Log.d("test1", "setIdExercise : " + this.idExercise);
    }
}
