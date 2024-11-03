package com.example.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.objects.Workout;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder>{

    Context context;
    List<Workout> groups;

    public WorkoutAdapter(Context context, List<Workout> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutHolder((LayoutInflater.from(context).inflate(R.layout.exercisegroup_view, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {
        holder.tv_name.setText(groups.get(position).getWorkout().get(0).getName().toString());
        holder.tv_sets.setText(String.valueOf(groups.get(position).getWorkout().get(0).getSets().size()));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
