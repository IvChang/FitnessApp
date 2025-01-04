package com.example.fitnessapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.ListAdapter
import com.example.fitnessapp.ListAdapter.Companion.VIEW_TYPE_SET
import com.example.fitnessapp.R
import com.example.fitnessapp.SetHolder
import com.example.fitnessapp.objects.Exercise
import com.example.fitnessapp.objects.Set
import com.example.fitnessapp.objects.Workout
import java.util.Collections

class WorkoutFragment : Fragment(), OnItemInteractionListener {
    var fragmentView: View? = null
    private var rv_workout: RecyclerView? = null


    var exercises: ArrayList<Exercise>? = null
    var selectedDay: String = "Monday"
    var filteredListWorkouts: MutableList<Workout> = ArrayList()
    val listDays = ArrayList<String>()
    var adapterWorkout: ArrayAdapter<Workout>? = null
    var listAdapter: ListAdapter? = null
    var selectedWorkoutIndex: Int = 0

    val simpleCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return if(viewHolder.itemViewType == VIEW_TYPE_SET && (viewHolder as SetHolder).isEditMode) {
                makeMovementFlags(UP or DOWN or START or END, 0)
            } else {
                0
            }
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            if (target.itemViewType == VIEW_TYPE_SET && (target as SetHolder).indexExercise == (viewHolder as SetHolder).indexExercise) {
                var setHolder = viewHolder as SetHolder
                var targetSetList = exercises!!.get(setHolder.indexExercise).sets

                var fromPosition = viewHolder.adapterPosition
                var toPosition = target.adapterPosition

                val fromIndex = targetSetList[setHolder.set!!.indexSet].indexSet
                val toIndex = targetSetList[(target as SetHolder).set!!.indexSet].indexSet
                val holderFromIndex = setHolder.set!!.indexSet
                val holderToIndex = (target as SetHolder).set!!.indexSet

                val tempIndex = fromIndex

                targetSetList[holderFromIndex].indexSet = toIndex
                targetSetList[holderToIndex].indexSet = tempIndex

                Collections.swap(targetSetList, setHolder.set!!.indexSet, (target as SetHolder).set!!.indexSet)

                rv_workout!!.adapter!!.notifyItemMoved(fromPosition, toPosition)
            }

            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

    }
    val itemTouchHelper = ItemTouchHelper(simpleCallback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_workout, container, false)

        rv_workout = fragmentView?.findViewById(R.id.rv_workout)
        val sp_day = fragmentView?.findViewById<Spinner>(R.id.sp_day)
        val sp_workoutName: Spinner? = fragmentView?.findViewById(R.id.sp_workoutName)
        val btn_addWorkout: Button? = fragmentView?.findViewById(R.id.btn_addWorkout)
        val btn_modifyWorkout: Button? = fragmentView?.findViewById(R.id.btn_modifyWorkout)

        btn_addWorkout!!.setOnClickListener{v: View? ->
            Log.d("test1", "addWorkout")
        }

        btn_modifyWorkout!!.setOnClickListener{v: View? ->
            Log.d("test1", "modifyWorkout")
        }


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

        val workout = Workout(ArrayList(), "Workout A", "Monday")
        workout.workout.add(Exercise(1, "Pushups", "Bodyweight", "", sets, true, 0, false))
        workout.workout.add(Exercise(2, "Pushups2", "Bodyweight", "", sets2, true, 1, false))
        workout.workout.add(Exercise(3, "Pushups3", "Bodyweight", "", sets3, true, 2, false))
        workout.workout.add(Exercise(4, "Pushups4", "Bodyweight", "", sets4, true, 3, false))
        workout.workout.add(Exercise(5, "Pushups5", "Bodyweight", "", sets5, true, 4, false))
        workout.workout.add(Exercise(6, "Pushups6", "Bodyweight", "", sets6, true, 5, false))



        val listWorkouts: MutableList<Workout> = ArrayList()
        listWorkouts.add(workout)

        val sets11 = ArrayList<Set>()
        sets11.add(Set(1, 1, "", 0))
        sets11.add(Set(8, 1, "", 1))
        sets11.add(Set(8, 1, "", 2))

        val workout2 = Workout(ArrayList(), "Workout B", "Monday")
        workout2.workout.add(Exercise(1, "Pushups11", "Bodyweight", "", sets11, true, 0, false))

        listWorkouts.add(workout2)



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

        sp_day?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDay = listDays[position]
                Log.d("test1", selectedDay)

                filteredListWorkouts.clear()

                for (workout in listWorkouts) {
                    Log.d("test1", workout.name + " (" + workout.day + ")")
                    if (workout.day.equals(selectedDay)) {
                        Log.d("test1", "${workout.name} is in")
                        filteredListWorkouts.add(workout)
                    }
                }
                adapterWorkout!!.notifyDataSetChanged()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        adapterWorkout = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filteredListWorkouts)
        adapterWorkout!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_workoutName?.adapter = adapterWorkout


        sp_workoutName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               selectedWorkoutIndex = position
                Log.d("test1", "filteredListWorkouts : ${filteredListWorkouts.size}")
                if (filteredListWorkouts.size > 0) {
                    exercises = filteredListWorkouts[selectedWorkoutIndex].workout
                    Log.d("test1", "name : ${filteredListWorkouts[selectedWorkoutIndex].name}")
                }
                if (listAdapter != null) {

                    listAdapter!!.updateExercises(exercises)
                    rv_workout!!.adapter!!.notifyDataSetChanged()
                    Log.d("test1", "itemCount : ${rv_workout!!.adapter!!.itemCount}")
                } else if (filteredListWorkouts.size > 0){
                    listAdapter = ListAdapter(requireContext(), exercises, this@WorkoutFragment)
                    rv_workout?.setLayoutManager(LinearLayoutManager(context))
                    rv_workout?.setAdapter(listAdapter)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("test1", "Nothing is selected")
                listAdapter!!.updateExercises(ArrayList<Exercise>())
                rv_workout!!.adapter!!.notifyDataSetChanged()
            }

        }

        //Log.d("test1", "listAdapter : workout name : ${selectedWorkout!!.name} , ${selectedWorkout!!.workout.size}")

        if (filteredListWorkouts.size > 0) {
            listAdapter = ListAdapter(requireContext(), exercises, this)
        } else {
            listAdapter = null
        }


        rv_workout?.setLayoutManager(LinearLayoutManager(context))
        rv_workout?.setAdapter(listAdapter)


        itemTouchHelper.attachToRecyclerView(rv_workout)

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
        Log.d("test1", "exercises[${indexExercise}].sets[${indexSet}]")
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

    override fun onMoveExerciseButtonClick(position: Int, indexExercise: Int, direction: String) {
        val tempExercise = exercises!![indexExercise]
        if (direction.equals("Up")) {
            if (indexExercise > 0) {
                val pastIndex = exercises!![indexExercise].indexExercise
                val newIndex = exercises!![indexExercise - 1].indexExercise

                exercises!![indexExercise] = exercises!![indexExercise - 1]
                exercises!![indexExercise - 1] = tempExercise

                exercises!![newIndex].indexExercise = indexExercise - 1
                exercises!![pastIndex].indexExercise = indexExercise

                val posPrevExercise = position - exercises!![pastIndex].sets.size
                val bothSetSize = exercises!![indexExercise].sets.size + exercises!![indexExercise - 1].sets.size + 2

                rv_workout!!.adapter!!.notifyItemRangeChanged(posPrevExercise, bothSetSize)

            } else {
                Toast.makeText(requireContext(), "Unable to move up", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (indexExercise < exercises!!.size - 1) {
                val pastIndex = exercises!![indexExercise].indexExercise
                val newIndex = exercises!![indexExercise + 1].indexExercise

                exercises!![indexExercise] = exercises!![indexExercise + 1]
                exercises!![indexExercise + 1] = tempExercise

                exercises!![newIndex].indexExercise = indexExercise + 1
                exercises!![pastIndex].indexExercise = indexExercise

                val bothSetSize = exercises!![indexExercise].sets.size + exercises!![indexExercise + 1].sets.size + 2
                rv_workout!!.adapter!!.notifyItemRangeChanged(position, bothSetSize)
            } else {
                Toast.makeText(requireContext(), "Unable to move down", Toast.LENGTH_SHORT).show()
            }
        }

    }

}