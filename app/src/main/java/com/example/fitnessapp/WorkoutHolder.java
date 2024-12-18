package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.fragments.OnItemInteractionListener;

public class WorkoutHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    TextView tv_sets;
    EditText et_note;
    AutoCompleteTextView actv_name;
    ImageView iv_toggleSets, iv_exerciseOptions;
    private Exercise exercise;
    private int idExercise;
    private boolean inEditMode = false;
    String[] exerciseList;


    public WorkoutHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        actv_name = itemView.findViewById(R.id.actv_name);
        et_note = itemView.findViewById(R.id.et_note);
        tv_sets = itemView.findViewById(R.id.tv_sets);
        iv_toggleSets = itemView.findViewById(R.id.iv_toggleSets);
        iv_exerciseOptions = itemView.findViewById(R.id.iv_exerciseOptions);

        // Pour initialiser la liste d'exercices pour l'autocomplétion
        initializeExerciseList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_list_item_1, exerciseList);
        actv_name.setAdapter(adapter);


        // listener pour cacher/dévoiler les sets
        iv_toggleSets.setOnClickListener(v -> {
            listener.onToggleButtonClick(getAdapterPosition(), this.idExercise);
            iv_toggleSets.setRotation(iv_toggleSets.getRotation() - 180);
        });

        // listener pour afficher le menu popup de l'exercice
        iv_exerciseOptions.setOnClickListener(v -> {
            if (inEditMode) {

                listener.onModifyExerciseButtonClick(getAdapterPosition(), this.idExercise, actv_name.getText().toString()
                        , et_note.getText().toString());

                iv_exerciseOptions.setImageResource(R.drawable.three_dots);
                inEditMode = false;
                actv_name.setClickable(false);
                actv_name.setFocusable(false);
                actv_name.setFocusableInTouchMode(false);
                et_note.setClickable(false);
                et_note.setFocusable(false);
                et_note.setFocusableInTouchMode(false);
            } else {
                PopupMenu popupMenu = new PopupMenu(itemView.getContext(), iv_exerciseOptions);
                popupMenu.setOnMenuItemClickListener(this);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.exercisemenu, popupMenu.getMenu());
                popupMenu.show();
            }

        });

    }

    public void bindWorkout(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pop_mnu_modify) {
            Log.d("test1", "modify");

            actv_name.setClickable(true);
            actv_name.setFocusable(true);
            actv_name.setFocusableInTouchMode(true);
            et_note.setClickable(true);
            et_note.setFocusable(true);
            et_note.setFocusableInTouchMode(true);
            actv_name.requestFocus();
            iv_exerciseOptions.setImageResource(R.drawable.green_checkmark);
            inEditMode = true;
        } else if (item.getItemId() == R.id.pop_mnu_remove) {
            Log.d("test1", "remove");
        }
        return false;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public void initializeExerciseList() {
        exerciseList = new String[]{"Push-Ups", "Pull-Ups", "Deadlift", "Inverted Row", "Squat"};
    }

}
