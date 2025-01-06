package com.example.fitnessapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnessapp.objects.Exercise
import com.example.fitnessapp.objects.Set
import com.example.fitnessapp.objects.Workout

class WorkoutActivity : AppCompatActivity() {


    var tv_title: TextView? = null
    var et_workoutName: EditText? = null
    var sp_workoutDay: Spinner? = null
    var btn_confirmWorkout: Button? = null

    var listWorkouts: ArrayList<Workout> = ArrayList()

    var selectedDay: String = "Monday"
    val listDays = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tv_title = findViewById(R.id.tv_title)
        et_workoutName = findViewById(R.id.et_workoutName)
        sp_workoutDay = findViewById(R.id.sp_workoutDay)
        btn_confirmWorkout = findViewById(R.id.btn_confirmWorkout)

        var retourWorkout: Intent = intent
        var b_workouts: Bundle? = retourWorkout.extras
        listWorkouts = b_workouts!!.getParcelableArrayList("LIST")!!

        Log.d("test1", "listWorkouts.size : ${listWorkouts.size}")

        listDays.add("Monday")
        listDays.add("Tuesday")
        listDays.add("Wednesday")
        listDays.add("Thursday")
        listDays.add("Friday")
        listDays.add("Saturday")
        listDays.add("Sunday")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listDays)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_workoutDay?.adapter = adapter

        sp_workoutDay?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDay = listDays[position]
                Log.d("test1", "selected ${selectedDay}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        btn_confirmWorkout?.setOnClickListener{v: View? ->
            Log.d("test1", "confirmWorkout -> saving ${et_workoutName!!.text.toString()} for ${selectedDay}")

            val sets13 = ArrayList<Set>()
            sets13.add(Set(1, 1, "", 0))
            sets13.add(Set(8, 1, "", 1))
            sets13.add(Set(8, 1, "", 2))
            val workout = Workout(ArrayList(), et_workoutName!!.text.toString(), selectedDay)
            workout.workout.add(Exercise(1, "Pushups13", "Bodyweight", "", sets13, true, 0, false))

            val resultIntent = Intent()
            var b_result: Bundle = Bundle()
            b_result.putParcelable("WORKOUT", workout)
            resultIntent.putExtras(b_result)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}