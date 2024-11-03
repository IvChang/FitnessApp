package com.example.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.objects.ExerciseGroup;

import java.util.List;

public class ExerciseGroupAdapter extends RecyclerView.Adapter<ExerciseGroupHolder>{

    Context context;
    List<ExerciseGroup> groups;

    public ExerciseGroupAdapter(Context context, List<ExerciseGroup> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public ExerciseGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseGroupHolder((LayoutInflater.from(context).inflate(R.layout.exercisegroup_view, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseGroupHolder holder, int position) {
        holder.tv_name.setText(groups.get(position).getGroup().get(0).getName().toString());
        holder.tv_sets.setText(String.valueOf(groups.get(position).getGroup().get(0).getSets().size()));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
