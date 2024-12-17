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
        sets6.add(new Set(10, 0, "", 3));
        exercises = new ArrayList<>();
        exercises.add(new Exercise(1, "Pushups", "Bodyweight", "", sets, true, 0));
        exercises.add(new Exercise(2, "Pushups2", "Bodyweight", "", sets2, true, 1));
        exercises.add(new Exercise(3, "Pushups3", "Bodyweight", "", sets3, true, 2));
        exercises.add(new Exercise(4, "Pushups4", "Bodyweight", "", sets4, true, 3));
        exercises.add(new Exercise(5, "Pushups5", "Bodyweight", "", sets5, true, 4));
        exercises.add(new Exercise(6, "Pushups6", "Bodyweight", "", sets6, true, 5));

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
    public void onToggleButtonClick(int position, int idExercise) {

        int posExercise = 0;
        while (exercises.get(posExercise).getId() != idExercise && posExercise < exercises.size()) {
            posExercise++;
        }
        Log.d("test1", "getId() : " + exercises.get(posExercise).getId() + " idExercise : " + idExercise);
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

}