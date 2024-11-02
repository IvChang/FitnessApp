package com.example.fitnessapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fitnessapp.fragments.ProgressFragment;
import com.example.fitnessapp.fragments.WorkoutFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("Test", String.valueOf(position));
        switch (position) {
            case 0:
                //Log.d("Test", String.valueOf(position));
                return new WorkoutFragment();
            case 1:
                return new ProgressFragment();
            default:
                return new WorkoutFragment();
        }
    }

    //nb of tabs
    @Override
    public int getItemCount() {
        return 2;
    }
}
