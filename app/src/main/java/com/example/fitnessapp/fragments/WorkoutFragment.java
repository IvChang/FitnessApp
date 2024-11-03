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

import com.example.fitnessapp.ExerciseGroupAdapter;
import com.example.fitnessapp.R;
import com.example.fitnessapp.objects.Exercise;
import com.example.fitnessapp.objects.ExerciseGroup;
import com.example.fitnessapp.objects.Set;

import java.util.ArrayList;
import java.util.List;


public class WorkoutFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workout, container, false);

        RecyclerView rv_exerciseGroup = view.findViewById(R.id.rv_exerciseGroup);
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
        ExerciseGroup exerciseGroup = new ExerciseGroup(exercises);

        List<ExerciseGroup> group = new ArrayList<ExerciseGroup>();
        group.add(exerciseGroup);

        rv_exerciseGroup.setLayoutManager(new LinearLayoutManager( getContext()));
        rv_exerciseGroup.setAdapter(new ExerciseGroupAdapter(requireContext(), group));

        return view;
    }
}