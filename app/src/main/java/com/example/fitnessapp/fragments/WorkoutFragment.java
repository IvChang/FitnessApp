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
        sets.add(new Set(8, 0, ""));
        sets.add(new Set(8, 0, ""));
        sets.add(new Set(8, 0, ""));

        ArrayList<Set> sets2 = new ArrayList<>();
        sets2.add(new Set(7, 5, ""));
        sets2.add(new Set(7, 5, ""));
        sets2.add(new Set(7, 5, ""));
        sets2.add(new Set(17, 5, ""));
        sets2.add(new Set(17, 5, ""));

        ArrayList<Set> sets3 = new ArrayList<>();
        sets3.add(new Set(9, 1, ""));



        ArrayList<Set> sets6 = new ArrayList<>();
        sets6.add(new Set(10, 0, ""));
        sets6.add(new Set(10, 0, ""));
        sets6.add(new Set(10, 0, ""));
        sets6.add(new Set(10, 0, ""));
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Pushups", "Bodyweight", "", sets));
        exercises.add(new Exercise("Pushups2", "Bodyweight", "", sets2));
        exercises.add(new Exercise("Pushups3", "Bodyweight", "", sets3));
        exercises.add(new Exercise("Pushups4", "Bodyweight", "", sets));
        exercises.add(new Exercise("Pushups5", "Bodyweight", "", sets));
        exercises.add(new Exercise("Pushups6", "Bodyweight", "", sets6));

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
    public void onToggleButtonClick(int position, String name) {

        int posExercise = 0;
        while (!exercises.get(posExercise).getName().equals(name) && posExercise < exercises.size()) {
            posExercise++;
        }

        for (int i = 0; i < exercises.get(posExercise).getSets().size(); i++) {
            exercises.get(posExercise).getSets().get(i).setIsVisible(!exercises.get(posExercise).getSets().get(i).getIsVisible());
        }

        // Rafraichit les holders correspondants
        rv_workout.getAdapter().notifyItemRangeChanged(position + 1, exercises.get(posExercise).getSets().size());

    }

}