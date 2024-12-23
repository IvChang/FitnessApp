package com.example.fitnessapp;

import android.content.Context;


import android.util.Log;
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
    private static final int VIEW_TYPE_ADDSET = 2;
    private static final int VIEW_TYPE_ADDEXERCISE = 3;
    private OnItemInteractionListener listener;

    Context context;
    List<Workout> groups;
    private int indexSets = 0;

    public ListAdapter(Context context, List<Workout> groups, OnItemInteractionListener listener) {
        this.context = context;
        this.groups = groups;
        this.listener = listener;
    }

    // Décide et crée le holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_EXERCISE) {
            view = (LayoutInflater.from(context).inflate(R.layout.exercisegroup_view, parent, false));
            return new WorkoutHolder(view, listener);
        } else if (viewType == VIEW_TYPE_ADDSET){
            view = (LayoutInflater.from(context).inflate(R.layout.add_set_view, parent, false));
            return new AddSetHolder(view, listener);
        } else if (viewType == VIEW_TYPE_SET){
            view = (LayoutInflater.from(context).inflate(R.layout.set_view, parent, false));
            return new SetHolder(view, listener);
        } else {
            view = (LayoutInflater.from(context).inflate(R.layout.add_exercise_view, parent, false));
            return new AddExerciseHolder(view,listener);
        }

    }
    //Pour modifier les widgets d'un setHolder à partir d'une autre classe en rafraichissant certaines positions
    // dans le RecyclerList, appellant onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        if (!payloads.isEmpty()) {
            for (Object payload : payloads) {
                if (holder.getItemViewType() == VIEW_TYPE_SET) {
                    SetHolder setHolder = (SetHolder) holder;
                    if (payload.equals("deletionMode")) {
                        setHolder.updateImageView(true);
                    } else if (payload.equals("normalMode")){
                        setHolder.updateImageView(false);
                    }
                    Exercise exercise = getExerciseForPosition(position);
                    indexSets = getSetIndexForPosition(position);
                    Set set = exercise.getSets().get(indexSets);
                    setHolder.setSet(set, exercise.getIndexExercise(), exercise.getIsEditMode());
                }
            }
        } else {
            // Appel l'autre onBindViewHolder
            super.onBindViewHolder(holder, position, payloads);

        }
    }

    // Modifier le holder et ses éléments
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Exercise exercise = getExerciseForPosition(position);
        if (holder.getItemViewType() == VIEW_TYPE_EXERCISE) {
            WorkoutHolder exerciseHolder = (WorkoutHolder) holder;
            exerciseHolder.setIndexExercise(exercise.getIndexExercise());

            exerciseHolder.actv_name.setText(exercise.getName());
            exerciseHolder.tv_sets.setText(exercise.getSets().size() + " SETS");
            exerciseHolder.bindWorkout(exercise);

        } else if (holder.getItemViewType() == VIEW_TYPE_ADDSET) {
            AddSetHolder addSetHolder = (AddSetHolder) holder;
            addSetHolder.setIndexExercise(exercise.getIndexExercise());

            if (exercise != null && exercise.getSetsAreVisible()) {
                ViewGroup.LayoutParams params = addSetHolder.itemView.getLayoutParams();
                params.height = 120;
                addSetHolder.itemView.setLayoutParams(params);
            } else {
                ViewGroup.LayoutParams params = addSetHolder.itemView.getLayoutParams();
                params.height = 0;
                addSetHolder.itemView.setLayoutParams(params);
            }

        } else if (holder.getItemViewType() == VIEW_TYPE_SET) {

            if (exercise != null) {
                SetHolder setHolder = (SetHolder) holder;

                indexSets = getSetIndexForPosition(position);
                Set set = exercise.getSets().get(indexSets);
                setHolder.setSet(set, exercise.getIndexExercise(), exercise.getIsEditMode());

                setHolder.tv_setNo.setText(String.valueOf(indexSets + 1));
                if (set.getIsModified()) {
                    setHolder.et_weight.setText(String.valueOf(set.getNewWeight()));
                    setHolder.et_reps.setText(String.valueOf(set.getNewReps()));
                    setHolder.iv_modifySet.setVisibility(View.VISIBLE);
                } else {
                    setHolder.et_weight.setText(String.valueOf(set.getWeight()));
                    setHolder.et_reps.setText(String.valueOf(set.getReps()));
                    setHolder.iv_modifySet.setVisibility(View.GONE);
                }

                if (exercise.getIsEditMode()) {
                    setHolder.iv_modifySet.setVisibility(View.VISIBLE);
                } else {
                    setHolder.iv_modifySet.setVisibility(View.GONE);
                }


                if (set.getIsVisible()) {
                    ViewGroup.LayoutParams params = setHolder.itemView.getLayoutParams();
                    params.height = 100;
                    setHolder.itemView.setLayoutParams(params);
                    exercise.setSetsAreVisible(true);
                } else {
                    ViewGroup.LayoutParams params = setHolder.itemView.getLayoutParams();
                    params.height = 0;
                    setHolder.itemView.setLayoutParams(params);
                    exercise.setSetsAreVisible(false);
                }
            }
        }
    }

    //Trouve l'exercise correspondant en parcourant la liste d'exercices en meme temps de vérifier à
    // chaque fois si la position de l'exercice qu'on parcoure correspond à la position entrée
    private Exercise getExerciseForPosition(int position) {
        int index = 0;
        for (Exercise exercise : groups.get(0).getWorkout()) {
            if (position >= index && position <= (index + exercise.getSets().size() + 1)) { // +1 pour prendre en compte le button addSet
                return exercise;
            }
            index += exercise.getSets().size() + 2;
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
            index += exercise.getSets().size() + 2;
        }
        return -1;
    }

    // Determine quel type (exercise, set ou addSet) afficher dans recyclerView
    @Override
    public int getItemViewType(int position) {
        if (isExercise(position)) {
            return VIEW_TYPE_EXERCISE;
        } else if (isEndOfSets(position)) {
            return VIEW_TYPE_ADDSET;
        } else if (isEndOfList(position)) {
            return VIEW_TYPE_ADDEXERCISE;
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
            if (position == 1) {
                index += exercise.getSets().size() + 1;
            } else {
                index += exercise.getSets().size() + 2;
            }

        }
        return false;
    }

    // Vérifie si l'élément est à la fin d'un set en déterminant toutes les positions qui sont une fin de sets
    // et vérifier si la position actuelle correspond à l'une de celles-ci
    private boolean isEndOfSets(int position) {
        int index = 0;
        for (Exercise exercise : groups.get(0).getWorkout()) {

            if (index == 0) {
                index += exercise.getSets().size() + 1;
            } else {
                index += exercise.getSets().size() + 2;
            }

            if (position == index) {
                return true;
            }
        }
        return false;
    }

    private boolean isEndOfList(int position) {
        int index = 0;
        boolean isEndOfList = false;
        for (Exercise exercise : groups.get(0).getWorkout()) {

            if (index == 0) {
                index += exercise.getSets().size() + 1;
            } else {
                index += exercise.getSets().size() + 2;
            }

        }
        //Log.d("test1", "index : " + index); // the current last position (last add set) is position 32
        if (position == index + 1) {
            isEndOfList = true;
        }
        return isEndOfList;
    }


    public void updateImageView(int position, int setSize, boolean deletionMode) {
        if (deletionMode) {
            notifyItemRangeChanged(position + 1, setSize, "deletionMode");
        } else {
            notifyItemRangeChanged(position + 1, setSize, "normalMode");
        }

    }


    @Override
    public int getItemCount() {
        int nbSets = 0;
        for (int i = 0; i < groups.get(0).getWorkout().size(); i++) {
            nbSets += groups.get(0).getWorkout().get(i).getSets().size() + 1;
        }
        return groups.get(0).getWorkout().size() + nbSets + 1;

    }



}
