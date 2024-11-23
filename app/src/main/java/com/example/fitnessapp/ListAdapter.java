package com.example.fitnessapp;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.objects.Set;
import com.example.fitnessapp.objects.Workout;
import com.example.fitnessapp.fragments.OnItemInteractionListener;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_EXERCISE = 0;
    private static final int VIEW_TYPE_SET = 1;
    private OnItemInteractionListener listener;

    Context context;
    List<Workout> groups;
    private int indexSets = 0;

    public ListAdapter(Context context, List<Workout> groups, OnItemInteractionListener listener) {
        this.context = context;
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_EXERCISE) {
            view = (LayoutInflater.from(context).inflate(R.layout.exercisegroup_view, parent, false));
            return new WorkoutHolder(view, listener);
        } else {
            view = (LayoutInflater.from(context).inflate(R.layout.set_view, parent, false));
            return new SetHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Exercise exercise = getExerciseForPosition(position);
        if (holder.getItemViewType() == VIEW_TYPE_EXERCISE) {
            WorkoutHolder exerciseHolder = (WorkoutHolder) holder;

            exerciseHolder.tv_name.setText(exercise.getName());
            exerciseHolder.tv_sets.setText(exercise.getSets().size() + " SETS");
            exerciseHolder.bindWorkout(exercise);

        } else {
            SetHolder setHolder = (SetHolder) holder;
            indexSets = getSetIndexForPosition(position);
            Set set = exercise.getSets().get(indexSets);

            setHolder.tv_setNo.setText(String.valueOf(indexSets + 1));
            setHolder.et_weight.setText(String.valueOf(set.getWeight()));
            setHolder.et_reps.setText(String.valueOf(set.getReps()));

            if (set.getIsVisible()) {
                ViewGroup.LayoutParams params = setHolder.itemView.getLayoutParams();
                params.height = 100;
                setHolder.itemView.setLayoutParams(params);
            } else {
                ViewGroup.LayoutParams params = setHolder.itemView.getLayoutParams();
                params.height = 0;
                setHolder.itemView.setLayoutParams(params);
            }

        }

    }

    //Trouve l'exercise correspondant en parcourant la liste d'exercices en meme temps de vérifier à
    // chaque fois si la position de l'exercice qu'on parcoure correspond à la position entrée
    private Exercise getExerciseForPosition(int position) {
        int index = 0;
        for (Exercise exercise : groups.get(0).getWorkout()) {
            if (position >= index && position <= (index + exercise.getSets().size())) {
                return exercise;
            }
            index += exercise.getSets().size() + 1;
        }
        return null;
    }


    //Determine l'index du set en comparant la position entrée à chacun des positions qui délimite
    //chaque exercice dans le but de trouver dans quel exercice le set recherché se trouve, puis
    // on soustrait la position entrée de l'index délimitant le bon exercice pour trouver l'index pointant
    // le set de l'exercice.
    private int getSetIndexForPosition(int position) {
        int index = 0;
        for (Exercise exercise : groups.get(0).getWorkout()) {
            int endIndex = index + exercise.getSets().size();

            if (position > index && position <= endIndex) {
                return position - index - 1; //-1 pour exclure l'exercice
            }
            index += exercise.getSets().size() + 1;
        }
        return -1;
    }

    // Determine quel type (exercise ou set) afficher dans recyclerView
    @Override public int getItemViewType(int position) {
        if (isExercise(position)) {
            return VIEW_TYPE_EXERCISE;
        } else {
            return VIEW_TYPE_SET;
        }
    }

    // Vérifie si l'élément est un exercice en déterminant toutes les positions qui sont un exercice
    // et vérifier si la position actuelle correspond à l'une de celles-ci
    private boolean isExercise(int position) {
        int index = 0;
        for (Exercise exercise : groups.get(0).getWorkout()) {
            if (position == index) {
                return true;
            }
            index += exercise.getSets().size() + 1;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        int nbSets = 0;
        for (int i = 0; i < groups.get(0).getWorkout().size(); i++) {
            nbSets += groups.get(0).getWorkout().get(i).getSets().size();
        }
        return groups.get(0).getWorkout().size() + nbSets;

    }
}
