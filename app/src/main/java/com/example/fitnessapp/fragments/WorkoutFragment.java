package com.example.fitnessapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.ListAdapter;
import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.objects.Set;
import com.example.fitnessapp.objects.Workout;

import java.util.ArrayList;
import java.util.List;


public class WorkoutFragment extends Fragment implements OnItemInteractionListener {

    View view;
    private RecyclerView rv_workout;

    ArrayList<Exercise> exercises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workout, container, false);

        rv_workout = view.findViewById(R.id.rv_workout);
        Spinner sp_day = view.findViewById(R.id.sp_day);

        // spinner pour les jours
        ArrayList<String> listDays = new ArrayList<>();

        listDays.add("Monday");
        listDays.add("Tuesday");
        listDays.add("Wednesday");
        listDays.add("Thursday");
        listDays.add("Friday");
        listDays.add("Saturday");
        listDays.add("Sunday");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listDays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(adapter);

        // Exercices temporaires
        ArrayList<Set> sets = new ArrayList<>();
        sets.add(new Set(1, 0, "", 0));
        sets.add(new Set(8, 0, "", 1));
        sets.add(new Set(8, 0, "", 2));

        ArrayList<Set> sets2 = new ArrayList<>();
        sets2.add(new Set(2, 5, "", 0));
        sets2.add(new Set(7, 5, "", 1));
        sets2.add(new Set(7, 5, "", 2));
        sets2.add(new Set(17, 5, "", 3));
        sets2.add(new Set(17, 5, "", 4));

        ArrayList<Set> sets3 = new ArrayList<>();
        sets3.add(new Set(3, 1, "",0));

        ArrayList<Set> sets4 = new ArrayList<>();
        sets4.add(new Set(4, 0, "", 0));
        sets4.add(new Set(10, 0, "", 1));
        sets4.add(new Set(10, 0, "", 2));
        sets4.add(new Set(10, 0, "", 3));

        ArrayList<Set> sets5 = new ArrayList<>();
        sets5.add(new Set(5, 0, "", 0));
        sets5.add(new Set(6, 0, "", 1));
        sets5.add(new Set(10, 0, "", 2));
        sets5.add(new Set(10, 0, "", 3));


        ArrayList<Set> sets6 = new ArrayList<>();
        sets6.add(new Set(7, 0, "", 0));
        sets6.add(new Set(10, 0, "", 1));
        sets6.add(new Set(10, 0, "", 2));
        exercises = new ArrayList<>();
        exercises.add(new Exercise(1, "Pushups", "Bodyweight", "", sets, true, 0, false));
        exercises.add(new Exercise(2, "Pushups2", "Bodyweight", "", sets2, true, 1, false));
        exercises.add(new Exercise(3, "Pushups3", "Bodyweight", "", sets3, true, 2, false));
        exercises.add(new Exercise(4, "Pushups4", "Bodyweight", "", sets4, true, 3, false));
        exercises.add(new Exercise(5, "Pushups5", "Bodyweight", "", sets5, true, 4, false));
        exercises.add(new Exercise(6, "Pushups6", "Bodyweight", "", sets6, true, 5, false));

        Workout workout = new Workout(exercises, "Workout A", "Monday");

        List<Workout> group = new ArrayList<Workout>();
        group.add(workout);

        rv_workout.setLayoutManager(new LinearLayoutManager( getContext()));
        rv_workout.setAdapter(new ListAdapter(requireContext(), group, this));

        return view;
    }

    // Methode venant de l'interface permettant de modifier la visibilité du SetHolder
    // à partir de WorkoutHolder
    @Override
    public void onToggleButtonClick(int position, int indexExercise) {

        int posExercise = 0;
        while (exercises.get(posExercise).getIndexExercise() != indexExercise && posExercise < exercises.size()) {
            posExercise++;
        }
        Log.d("test1", "getId() : " + exercises.get(posExercise).getIndexExercise() + " idExercise : " + indexExercise);
        for (int i = 0; i < exercises.get(posExercise).getSets().size(); i++) {
            exercises.get(posExercise).getSets().get(i).setIsVisible(!exercises.get(posExercise).getSets().get(i).getIsVisible());
        }

        // Rafraichit les holders correspondants
        rv_workout.getAdapter().notifyItemRangeChanged(position + 1, exercises.get(posExercise).getSets().size() + 1);

    }

    // Ajoute un set de l'exercice correspondant et rafraichir la liste sans nécessairement tout rafraichir pour rien
    // Rafraichit les positions partant de celles de WorkoutHolder de l'exercice correspondant jusqu'à la fin de la liste.
    @Override
    public void onAddSetButtonClick(int position, int indexExercise) {

        ArrayList<Set> listSets = exercises.get(indexExercise).getSets();
        listSets.add(new Set(0, 0, "", listSets.size()));
        Log.d("test1", "refresh from pos : " + (position - listSets.size()) + " to " + rv_workout.getAdapter().getItemCount());
        rv_workout.getAdapter().notifyItemRangeChanged(position - listSets.size(), rv_workout.getAdapter().getItemCount() - position);
    }

    @Override
    public void onModifyExerciseButtonClick(int position, int indexExercise, String name, String note) {
        exercises.get(indexExercise).setName(name);
        exercises.get(indexExercise).setNote(note);
        exercises.get(indexExercise).setIsEditMode(false);
    }

    @Override
    public void onModifySetButtonClick(int position, int indexExercise, int indexSet, int weight, int reps) {
        Log.d("test1", "newWeight: " + weight + " newReps : " + reps);
        Set set = exercises.get(indexExercise).getSets().get(indexSet);
        set.setWeight(weight);
        set.setReps(reps);
        set.setIsModified(false);
        rv_workout.getAdapter().notifyItemChanged(position);

    }

    @Override
    public void onModifySetModeButtonClick(boolean deletionMode, int position, int indexExercise) {
        Log.d("test1", "onModifySet deletion mode called");

        exercises.get(indexExercise).setIsEditMode(deletionMode);
        int setSize = exercises.get(indexExercise).getSets().size();

        ListAdapter adapter = (ListAdapter) rv_workout.getAdapter();
        adapter.updateImageView(position, setSize, deletionMode);

    }

    @Override
    public void onChangingSetStatus(String status, int indexExercise, int indexSet, int newWeight, int newReps) {
        Set set = exercises.get(indexExercise).getSets().get(indexSet);
        if (status.equals("isModifiedTrue")) {
            set.setIsModified(true);
            set.setNewWeight(newWeight);
            set.setNewReps(newReps);
        } else if (status.equals("isModifiedFalse")) {
            set.setIsModified(false);
        }
    }

    @Override
    public void onDeleteSetButtonClick(int position, int indexExercise, int indexSet) {
        Log.d("test1", "onDeleteSet called");
        exercises.get(indexExercise).getSets().remove(indexSet);

        for (int i = 0; i < exercises.get(indexExercise).getSets().size(); i++) {
            exercises.get(indexExercise).getSets().get(i).setIndexSet(i);
        }

        rv_workout.getAdapter().notifyItemRemoved(position);
    }


    @Override
    public void onDeleteExerciseButtonClick(int position, int indexExercise) {
        Log.d("test1", "onDeleteExercise called on " + indexExercise);

        exercises.remove(indexExercise);

        for (int i = indexExercise; i < exercises.size(); i++) {
            exercises.get(i).setIndexExercise(i);
            exercises.get(i).setId(i + 1);
        }

        if (indexExercise == exercises.size()) {
            rv_workout.getAdapter().notifyDataSetChanged();
        } else {
            rv_workout.getAdapter().notifyItemRemoved(position);
        }
    }

    @Override
    public void onAddExerciseButtonClick() {
        Log.d("test1", "onAddExercise called");
    }


}