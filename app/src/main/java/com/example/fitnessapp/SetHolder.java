package com.example.fitnessapp;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetHolder extends RecyclerView.ViewHolder {

    TextView tv_setNo;
    EditText et_weight, et_reps;


    public SetHolder(@NonNull View itemView) {
        super(itemView);

        tv_setNo = itemView.findViewById(R.id.tv_setNo);
        et_weight = itemView.findViewById(R.id.et_weight);
        et_reps = itemView.findViewById(R.id.et_reps);

    }
}
