package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    ImageView iv_modifySet;
    private Set set;
    private int indexExercise;
    boolean isEditMode;



    public SetHolder(@NonNull View itemView, OnItemInteractionListener listener) {
        super(itemView);

        tv_setNo = itemView.findViewById(R.id.tv_setNo);
        et_weight = itemView.findViewById(R.id.et_weight);
        et_reps = itemView.findViewById(R.id.et_reps);
        iv_modifySet = itemView.findViewById(R.id.iv_modifySet);


        et_weight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!et_weight.getText().toString().matches("") && !et_reps.getText().toString().matches("")) {
                    int newWeight = Integer.parseInt(et_weight.getText().toString());
                    int newReps = Integer.parseInt(et_reps.getText().toString());
                    if (newWeight != set.getWeight() || newReps != set.getReps()) {
                        iv_modifySet.setVisibility(View.VISIBLE);
                        iv_modifySet.setImageResource(R.drawable.green_checkmark);
                        listener.onChangingSetStatus("isModifiedTrue", indexExercise, set.getIndexSet(), newWeight, newReps);
                    } else {
                        if (!isEditMode) {
                            iv_modifySet.setVisibility(View.GONE);
                        }
                        iv_modifySet.setImageResource(R.drawable.delete);
                        listener.onChangingSetStatus("isModifiedFalse", indexExercise, set.getIndexSet(), newWeight, newReps);
                    }
                } else {
                    iv_modifySet.setImageResource(R.drawable.delete);
                    if (!isEditMode) {
                        iv_modifySet.setVisibility(View.GONE);
                    }
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
                if (!et_weight.getText().toString().matches("") && !et_reps.getText().toString().matches("")) {
                    int newWeight = Integer.parseInt(et_weight.getText().toString());
                    int newReps = Integer.parseInt(et_reps.getText().toString());
                    if (newWeight != set.getWeight() || newReps != set.getReps()) {
                        iv_modifySet.setVisibility(View.VISIBLE);
                        iv_modifySet.setImageResource(R.drawable.green_checkmark);
                        listener.onChangingSetStatus("isModifiedTrue", indexExercise, set.getIndexSet(), newWeight, newReps);
                    } else {
                        if (!isEditMode) {
                            iv_modifySet.setVisibility(View.GONE);
                        }
                        iv_modifySet.setImageResource(R.drawable.delete);
                        listener.onChangingSetStatus("isModifiedFalse", indexExercise, set.getIndexSet(), newWeight, newReps);
                    }
                } else {
                    iv_modifySet.setImageResource(R.drawable.delete);
                    if (!isEditMode) {
                        iv_modifySet.setVisibility(View.GONE);
                    }
                }
            }
        });

        iv_modifySet.setOnClickListener(v -> {
            Log.d("test1", "iv_modifySet called");
            if (isEditMode) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                alertDialogBuilder.setMessage("Are you sure to remove this set?");

                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDeleteSetButtonClick(getAdapterPosition(), indexExercise, set.getIndexSet());
                    }
                });

                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            } else {
                listener.onModifySetButtonClick(getAdapterPosition(), indexExercise, set.getIndexSet(), Integer.parseInt(et_weight.getText().toString())
                        , Integer.parseInt(et_reps.getText().toString()));
                iv_modifySet.setVisibility(View.GONE);
            }

        });

    }

    public void setSet(Set set, int indexExercise, boolean isEditMode) {
        this.set = set;
        this.indexExercise = indexExercise;
        this.isEditMode = isEditMode;

    }

    public void updateImageView(boolean deletionMode) {
        if (deletionMode) {
            iv_modifySet.setVisibility(View.VISIBLE);
        } else {
            if (!set.getIsModified()) {
                iv_modifySet.setVisibility(View.GONE);
            }
        }
        if (set.getIsModified()) {
            iv_modifySet.setImageResource(R.drawable.green_checkmark);
        } else {
            iv_modifySet.setImageResource(R.drawable.delete);
        }

    }

}
