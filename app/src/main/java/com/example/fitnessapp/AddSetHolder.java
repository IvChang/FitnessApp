package com.example.fitnessapp;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.fragments.OnItemInteractionListener;

public class AddSetHolder extends RecyclerView.ViewHolder{

    Button btn_addSet;

    public AddSetHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        btn_addSet = itemView.findViewById(R.id.btn_addSet);

        btn_addSet.setOnClickListener(v -> {
            listener.onAddSetButtonClick(0);
        });

    }

}
