package com.example.fitnessapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fitnessapp.WorkoutAdapter;
import com.example.fitnessapp.R;
import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.objects.Set;
import com.example.fitnessapp.objects.Workout;

import java.util.ArrayList;
import java.util.List;


public class WorkoutFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workout, container, false);

        RecyclerView rv_workout = view.findViewById(R.id.rv_workout);
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
        sets.add(new Set(8, 0, "Original"));
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Pushups", "Bodyweight", "", sets));
        Workout workout = new Workout(exercises, "Workout A", "Monday");

        List<Workout> group = new ArrayList<Workout>();
        group.add(workout);

        rv_workout.setLayoutManager(new LinearLayoutManager( getContext()));
        rv_workout.setAdapter(new WorkoutAdapter(requireContext(), group));

        return view;
    }
}