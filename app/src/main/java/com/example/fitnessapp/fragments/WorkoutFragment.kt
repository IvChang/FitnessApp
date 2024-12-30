package com.example.fitnessapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.ListAdapter
import com.example.fitnessapp.R
import com.example.fitnessapp.objects.Exercise
import com.example.fitnessapp.objects.Set
import com.example.fitnessapp.objects.Workout

class WorkoutFragment : Fragment(), OnItemInteractionListener {
    var fragmentView: View? = null
    private var rv_workout: RecyclerView? = null

    var exercises: ArrayList<Exercise>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_workout, container, false)

        rv_workout = fragmentView?.findViewById(R.id.rv_workout)
        val sp_day = fragmentView?.findViewById<Spinner>(R.id.sp_day)

        // spinner pour les jours
        val listDays = ArrayList<String>()

        listDays.add("Monday")
        listDays.add("Tuesday")
        listDays.add("Wednesday")
        listDays.add("Thursday")
        listDays.add("Friday")
        listDays.add("Saturday")
        listDays.add("Sunday")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listDays)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_day?.adapter = adapter

        // Exercices temporaires
        val sets = ArrayList<Set>()
        sets.add(Set(1, 1, "", 0))
        sets.add(Set(8, 1, "", 1))
        sets.add(Set(8, 1, "", 2))

        val sets2 = ArrayList<Set>()
        sets2.add(Set(2, 2, "", 0))
        sets2.add(Set(7, 2, "", 1))
        sets2.add(Set(7, 2, "", 2))
        sets2.add(Set(17, 2, "", 3))
        sets2.add(Set(17, 2, "", 4))

        val sets3 = ArrayList<Set>()
        sets3.add(Set(3, 3, "", 0))

        val sets4 = ArrayList<Set>()
        sets4.add(Set(4, 4, "", 0))
        sets4.add(Set(10, 4, "", 1))
        sets4.add(Set(10, 4, "", 2))
        sets4.add(Set(10, 4, "", 3))

        val sets5 = ArrayList<Set>()
        sets5.add(Set(5, 5, "", 0))
        sets5.add(Set(6, 5, "", 1))
        sets5.add(Set(10, 5, "", 2))
        sets5.add(Set(10, 5, "", 3))


        val sets6 = ArrayList<Set>()
        sets6.add(Set(7, 6, "", 0))
        sets6.add(Set(10, 6, "", 1))
        sets6.add(Set(10, 6, "", 2))
        exercises = ArrayList()
        exercises!!.add(Exercise(1, "Pushups", "Bodyweight", "", sets, true, 0, false))
        exercises!!.add(Exercise(2, "Pushups2", "Bodyweight", "", sets2, true, 1, false))
        exercises!!.add(Exercise(3, "Pushups3", "Bodyweight", "", sets3, true, 2, false))
        exercises!!.add(Exercise(4, "Pushups4", "Bodyweight", "", sets4, true, 3, false))
        exercises!!.add(Exercise(5, "Pushups5", "Bodyweight", "", sets5, true, 4, false))
        exercises!!.add(Exercise(6, "Pushups6", "Bodyweight", "", sets6, true, 5, false))

        val workout = Workout(exercises!!, "Workout A", "Monday")

        val group: MutableList<Workout> = ArrayList()
        group.add(workout)

        rv_workout?.setLayoutManager(LinearLayoutManager(context))
        rv_workout?.setAdapter(ListAdapter(requireContext(), group, this))

        return fragmentView
    }

    // Methode venant de l'interface permettant de modifier la visibilité du SetHolder
    // à partir de WorkoutHolder
    override fun onToggleButtonClick(position: Int, indexExercise: Int) {
        var posExercise = 0
        while (exercises!![posExercise].indexExercise != indexExercise && posExercise < exercises!!.size) {
            posExercise++
        }
        Log.d(
            "test1",
            "getId() : " + exercises!![posExercise].indexExercise + " idExercise : " + indexExercise
        )
        Log.d("test1", "posExercise : " + posExercise + " vs " + indexExercise)
        for (i in exercises!![posExercise].sets.indices) {
            exercises!![posExercise].sets[i].isVisible = !exercises!![posExercise].sets[i].isVisible
        }

        // Rafraichit les holders correspondants
        rv_workout!!.adapter!!.notifyItemRangeChanged(
            position + 1,
            exercises!![posExercise].sets.size + 1
        )
    }

    // Ajoute un set de l'exercice correspondant et rafraichir la liste sans nécessairement tout rafraichir pour rien
    // Rafraichit les positions partant de celles de WorkoutHolder de l'exercice correspondant jusqu'à la fin de la liste.
    override fun onAddSetButtonClick(position: Int, indexExercise: Int) {
        val listSets = exercises!![indexExercise].sets
        listSets.add(Set(0, 0, "", listSets.size))
        Log.d(
            "test1",
            "refresh from pos : " + (position - listSets.size) + " to " + rv_workout!!.adapter!!
                .itemCount
        )
        rv_workout!!.adapter!!
            .notifyItemRangeChanged(
                position - listSets.size,
                rv_workout!!.adapter!!.itemCount - position
            )
    }

    override fun onModifyExerciseButtonClick(
        position: Int,
        indexExercise: Int,
        name: String?,
        note: String?
    ) {
        exercises!![indexExercise].name = name!!
        exercises!![indexExercise].note = note!!
        exercises!![indexExercise].isEditMode = false

    }

    override fun onModifySetButtonClick(
        position: Int,
        indexExercise: Int,
        indexSet: Int,
        weight: Int,
        reps: Int
    ) {
        Log.d("test1", "newWeight: $weight newReps : $reps")
        val set = exercises!![indexExercise].sets[indexSet]
        set.weight = weight
        set.reps = reps
        set.isModified = false
        rv_workout!!.adapter!!.notifyItemChanged(position)
    }

    override fun onModifySetModeButtonClick(
        deletionMode: Boolean,
        position: Int,
        indexExercise: Int
    ) {
        Log.d("test1", "onModifySet deletion mode called")

        exercises!![indexExercise].isEditMode = deletionMode

        val setSize = exercises!![indexExercise].sets.size

        val adapter = rv_workout!!.adapter as ListAdapter?
        adapter!!.updateImageView(position, setSize, deletionMode)
        rv_workout!!.adapter!!.notifyItemChanged(position)
    }

    override fun onChangingSetStatus(
        isModified: Boolean,
        indexExercise: Int,
        indexSet: Int,
        newWeight: Int,
        newReps: Int
    ) {
        val set = exercises!![indexExercise].sets[indexSet]

        if (isModified) {
            set.isModified = true
            set.newWeight = newWeight
            set.newReps = newReps
            //Log.d("test1", "set " + indexExercise.toString() + " has newReps : " + set.newReps)
        } else {
            set.isModified = false
        }

    }

    override fun onDeleteSetButtonClick(position: Int, indexExercise: Int, indexSet: Int) {
        Log.d("test1", "onDeleteSet called")
        exercises!![indexExercise].sets.removeAt(indexSet)

        for (i in exercises!![indexExercise].sets.indices) {
            exercises!![indexExercise].sets[i].indexSet = i
        }

        rv_workout!!.adapter!!.notifyItemRangeChanged(position, exercises!![indexExercise].sets.size)
    }


    override fun onDeleteExerciseButtonClick(position: Int, indexExercise: Int) {
        Log.d("test1", "onDeleteExercise called on $indexExercise")

        exercises!!.removeAt(indexExercise)

        for (i in indexExercise until exercises!!.size) {
            exercises!![i].indexExercise = i
            exercises!![i].id = i + 1
        }
        rv_workout!!.adapter!!.notifyDataSetChanged()

    }

    override fun onAddExerciseButtonClick(position: Int) {
        Log.d("test1", "onAddExercise called")
        exercises!!.add(Exercise(exercises!!.size + 1, "New Exercise", "Bodyweight", "", ArrayList<Set>(), true, exercises!!.size, false))
        rv_workout!!.adapter!!.notifyItemInserted(position)
    }

}