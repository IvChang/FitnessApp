package com.example.fitnessapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.fragments.OnItemInteractionListener;
import com.example.fitnessapp.objects.Set;

public class SetHolder extends RecyclerView.ViewHolder {

    TextView tv_setNo;
    EditText et_weight, et_reps;
    ImageView iv_saveSet;
    private Set set;
    private int indexExercise;



    public SetHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        tv_setNo = itemView.findViewById(R.id.tv_setNo);
        et_weight = itemView.findViewById(R.id.et_weight);
        et_reps = itemView.findViewById(R.id.et_reps);
        iv_saveSet = itemView.findViewById(R.id.iv_saveSet);

        et_weight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!et_weight.getText().toString().matches("") && !et_reps.getText().toString().matches("")
                        && (Integer.parseInt(et_weight.getText().toString()) != set.getWeight()
                        || Integer.parseInt(et_reps.getText().toString()) != set.getReps())) {
                    iv_saveSet.setVisibility(View.VISIBLE);
                } else {
                    iv_saveSet.setVisibility(View.GONE);
                }
            }
        });

        et_reps.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!et_weight.getText().toString().matches("") && !et_reps.getText().toString().matches("")
                        && (Integer.parseInt(et_weight.getText().toString()) != set.getWeight()
                        || Integer.parseInt(et_reps.getText().toString()) != set.getReps())) {
                    iv_saveSet.setVisibility(View.VISIBLE);
                } else {
                    iv_saveSet.setVisibility(View.GONE);
                }
            }
        });

        iv_saveSet.setOnClickListener(v -> {
            listener.onModifySetButtonClick(getAdapterPosition(), indexExercise, set.getIndexSet(), Integer.parseInt(et_weight.getText().toString())
                    , Integer.parseInt(et_reps.getText().toString()));
            iv_saveSet.setVisibility(View.GONE);
        });


    }

    public void setSet(Set set, int indexExercise) {
        this.set = set;
        this.indexExercise = indexExercise;
    }
}
