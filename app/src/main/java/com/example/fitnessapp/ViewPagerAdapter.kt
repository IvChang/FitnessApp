package com.example.fitnessapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitnessapp.fragments.ProgressFragment
import com.example.fitnessapp.fragments.WorkoutFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->                 //Log.d("Test", String.valueOf(position));
                WorkoutFragment()

            1 -> ProgressFragment()
            else -> WorkoutFragment()
        }
    }

    //nb of tabs
    override fun getItemCount(): Int {
        return 2
    }
}
