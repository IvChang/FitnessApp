package com.example.fitnessapp.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.fitnessapp.holders.SetHolder
import com.example.fitnessapp.WorkoutActivity
import com.example.fitnessapp.objects.Exercise
import com.example.fitnessapp.objects.Set
import com.example.fitnessapp.objects.Workout
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Collections

class WorkoutFragment : Fragment(), OnItemInteractionListener {
    var fragmentView: View? = null
    private var rv_workout: RecyclerView? = null
    var sp_day: Spinner? = null


    val listWorkouts: ArrayList<Workout> = ArrayList()
    var exercises: ArrayList<Exercise>? = null
    var selectedDay: String = "Mon"
    var filteredListWorkouts: MutableList<Workout> = ArrayList()
    val listDays = ArrayList<String>()
    var adapterWorkout: ArrayAdapter<Workout>? = null
    var listAdapter: ListAdapter? = null
    var selectedWorkoutIndex: Int = 0

    var indexDay: Int? = -1

    var db: FirebaseDatabase? = null
    var ref: DatabaseReference? = null


    private lateinit var startForResult: ActivityResultLauncher<Intent>

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
                val targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
                var targetSetList = targetWorkout.workout.get(setHolder.indexExercise).sets

                var fromPosition = viewHolder.adapterPosition
                var toPosition = target.adapterPosition

                val fromIndex = targetSetList[setHolder.set!!.indexSet].indexSet
                val toIndex = targetSetList[(target as SetHolder).set!!.indexSet].indexSet
                val holderFromIndex = setHolder.set!!.indexSet
                val holderToIndex = (target as SetHolder).set!!.indexSet

                val tempIndex = fromIndex

                targetSetList[holderFromIndex].indexSet = toIndex
                targetSetList[holderToIndex].indexSet = tempIndex
                setHolder.set!!.indexSet = holderToIndex
                (target as SetHolder).set!!.indexSet = holderFromIndex

                Collections.swap(targetSetList, setHolder.set!!.indexSet, (target as SetHolder).set!!.indexSet)


                ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("test1", "successfully moved a set")
                    } else {
                        Toast.makeText(requireContext(), "Couldn't move a set", Toast.LENGTH_SHORT).show()
                    }
                }

                listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)

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
        sp_day = fragmentView?.findViewById<Spinner>(R.id.sp_day)
        val sp_workoutName: Spinner? = fragmentView?.findViewById(R.id.sp_workoutName)
        val btn_addWorkout: Button? = fragmentView?.findViewById(R.id.btn_addWorkout)
        val btn_modifyWorkout: Button? = fragmentView?.findViewById(R.id.btn_modifyWorkout)
        val btn_deleteWorkout: Button? = fragmentView?.findViewById(R.id.btn_deleteWorkout)

        db = FirebaseDatabase.getInstance()
        ref = db!!.getReference("Workouts")

        // recoit la réponse de WorkoutActivity pour ajouter/modifier un workout dans firebase
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("test1", "data received successfully")
                val bundle: Bundle? = result.data!!.extras
                val newWorkout: Workout? = bundle?.getParcelable("WORKOUT")
                val modification: Boolean? = bundle?.getBoolean("MODIFICATION")
                indexDay = bundle?.getInt("DAY")


                if (modification == false) {
                    val newWorkoutRef = ref!!.push()
                    val workoutId = newWorkoutRef.key
                    newWorkout!!.id = workoutId
                } else {
                    var index = 0;
                    while (listWorkouts[index].indexWorkout < newWorkout!!.indexWorkout) {
                        index++
                    }
                    if (index > 0) {
                        newWorkout.indexWorkout = listWorkouts[index - 1].indexWorkout + 1
                    }

                }


                ref!!.child(newWorkout.id!!).setValue(newWorkout).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("test1", "successfully added/updated a workout")
                    } else {
                        Toast.makeText(requireContext(), "Couldn't add/update a workout", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Log.d("test1", "error with result")
            }
        }


        btn_addWorkout?.setOnClickListener{v: View? ->
            Log.d("test1", "addWorkout")

            val workoutActivity = Intent(requireActivity(), WorkoutActivity::class.java)
            var b_workout: Bundle = Bundle()
            b_workout.putBoolean("MODIFICATION", false)
            b_workout.putInt("INDEX", listWorkouts[listWorkouts.size - 1].indexWorkout + 1)
            workoutActivity.putExtras(b_workout)
            startForResult.launch(workoutActivity)

        }

        btn_modifyWorkout?.setOnClickListener{v: View? ->
            Log.d("test1", "modifyWorkout")
            val workoutActivity = Intent(requireActivity(), WorkoutActivity::class.java)
            var b_workout: Bundle = Bundle()
            b_workout.putParcelable("WORKOUT", filteredListWorkouts[selectedWorkoutIndex])
            b_workout.putBoolean("MODIFICATION", true)
            b_workout.putInt("INDEX", filteredListWorkouts[selectedWorkoutIndex].indexWorkout)
            workoutActivity.putExtras(b_workout)
            startForResult.launch(workoutActivity)
        }

        btn_deleteWorkout?.setOnClickListener{v: View? ->
            Log.d("test1", "deleteWorkout")
            val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setMessage("Are you sure to remove this workout?")

            alertDialogBuilder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, which ->
                    val targetWorkout = listWorkouts.find {it.indexWorkout == filteredListWorkouts[selectedWorkoutIndex].indexWorkout}
                    //listWorkouts.removeAt(index)
                    ref!!.child(targetWorkout!!.id!!).removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("test1", "successfully removed a workout")
                        } else {
                            Toast.makeText(requireContext(), "Couldn't remove a workout", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            alertDialogBuilder.setNegativeButton(
                "NO",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                    }
                })
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }



        listDays.add("Mon")
        listDays.add("Tue")
        listDays.add("Wed")
        listDays.add("Thu")
        listDays.add("Fri")
        listDays.add("Sat")
        listDays.add("Sun")

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

                if (filteredListWorkouts.size > 0 && selectedWorkoutIndex == 0) {
                    exercises = filteredListWorkouts[selectedWorkoutIndex].workout
                    if (listAdapter != null) {
                        listAdapter!!.updateExercises(exercises)
                        rv_workout!!.adapter!!.notifyDataSetChanged()
                    }
                }
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
                Log.d("test1", "sp_workoutName is called")
               selectedWorkoutIndex = position

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

        if (filteredListWorkouts.size > 0) {
            listAdapter = ListAdapter(requireContext(), exercises, this)

        } else {
            listAdapter = null
        }

        referenceToDB()

        rv_workout?.setLayoutManager(LinearLayoutManager(context))
        rv_workout?.setAdapter(listAdapter)


        itemTouchHelper.attachToRecyclerView(rv_workout)

        return fragmentView
    }

    // écoute pour les changements dans le realtime database pour apporter les modifications correspondantes à l'arraylist
    // local de workouts
    fun referenceToDB() {

        ref!!.orderByChild("indexWorkout").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val workout = snapshot.getValue(Workout::class.java)
                Log.d("test1", "(ref) onChildAdded")
                if (workout != null) {
                    listWorkouts.add(workout)
                    Log.d("test1", "(ref) added ${workout.name}, size : ${listWorkouts.size}")
                    if (workout.day!!.equals(selectedDay)) {
                        filteredListWorkouts.add(workout)
                        if (filteredListWorkouts.size == 1) {
                            adapterWorkout!!.notifyDataSetChanged()
                        }
                    }
                    sp_day!!.setSelection(indexDay!!)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("test1", "onChildChanged")
                val workout = snapshot.getValue(Workout::class.java)
                if (workout != null) {
                    for (i in listWorkouts.indices) {
                        if(listWorkouts[i].indexWorkout == workout.indexWorkout) {
                            listWorkouts[i] = workout

                            filteredListWorkouts[selectedWorkoutIndex] = workout
                            adapterWorkout!!.notifyDataSetChanged()
                            break
                        }
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val workout = snapshot.getValue(Workout::class.java)
                if (workout != null) {
                    listWorkouts.removeIf { it.indexWorkout == workout.indexWorkout}

                    filteredListWorkouts.removeAt(selectedWorkoutIndex)
                    adapterWorkout!!.notifyDataSetChanged()

                    if (filteredListWorkouts.size > 0) {
                        if (selectedWorkoutIndex == filteredListWorkouts.size) {
                            selectedWorkoutIndex--
                        }
                        exercises = filteredListWorkouts[selectedWorkoutIndex].workout
                        if (listAdapter != null) {
                            listAdapter!!.updateExercises(exercises)
                            rv_workout!!.adapter!!.notifyDataSetChanged()
                        }

                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }



    // Methode venant de l'interface permettant de modifier la visibilité du SetHolder
    // à partir de WorkoutHolder
    override fun onToggleButtonClick(position: Int, indexExercise: Int) {
        val targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
        var posExercise = 0
        while (targetWorkout.workout[posExercise].indexExercise != indexExercise && posExercise < targetWorkout.workout.size) {
            posExercise++
        }
        Log.d(
            "test1",
            "getId() : " + targetWorkout.workout[posExercise].indexExercise + " idExercise : " + indexExercise
        )
        Log.d("test1", "posExercise : " + posExercise + " vs " + indexExercise)
        for (i in targetWorkout.workout[posExercise].sets.indices) {
            targetWorkout.workout[posExercise].sets[i].isVisible = !targetWorkout.workout[posExercise].sets[i].isVisible
        }

        listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)

        // Rafraichit les holders correspondants
        rv_workout!!.adapter!!.notifyItemRangeChanged(
            position + 1,
            targetWorkout.workout[posExercise].sets.size + 1
        )
    }

    // Ajoute un set de l'exercice correspondant et rafraichir la liste sans nécessairement tout rafraichir pour rien
    // Rafraichit les positions partant de celles de WorkoutHolder de l'exercice correspondant jusqu'à la fin de la liste.
    override fun onAddSetButtonClick(position: Int, indexExercise: Int) {

        val targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
        val targetListSets = targetWorkout.workout[indexExercise].sets
        targetListSets.add(Set(0, 0, "", targetListSets.size))

        var index = 0;
        while (listWorkouts[index].indexWorkout < targetWorkout.indexWorkout) {
            index++
        }
        if (index > 0) {
            targetWorkout.indexWorkout = listWorkouts[index - 1].indexWorkout + 1
        }



        ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("test1", "successfully added a new set")
            } else {
                Toast.makeText(requireContext(), "Couldn't add a new set", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("test1", "(test) size : ${filteredListWorkouts[selectedWorkoutIndex].workout[0].sets.size}")

        listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)


        rv_workout!!.adapter!!.notifyItemInserted(position)

    }

    override fun onModifyExerciseButtonClick(
        position: Int,
        indexExercise: Int,
        name: String?,
        note: String?
    ) {
        var targetWorkout = filteredListWorkouts[selectedWorkoutIndex]

        targetWorkout.workout[indexExercise].name = name!!
        targetWorkout.workout[indexExercise].note = note!!
        targetWorkout.workout[indexExercise].isEditMode = false

        ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("test1", "successfully updated a set")
            } else {
                Toast.makeText(requireContext(), "Couldn't update a set", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onModifySetButtonClick(
        position: Int,
        indexExercise: Int,
        indexSet: Int,
        weight: Int,
        reps: Int
    ) {
        var targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
        var targetSet = targetWorkout.workout[indexExercise].sets[indexSet]


        Log.d("test1", "newWeight: $weight newReps : $reps")
        targetSet.weight = weight
        targetSet.reps = reps
        targetSet.isModified = false

        var index = 0;
        while (listWorkouts[index].indexWorkout < targetWorkout.indexWorkout) {
            index++
        }
        if (index > 0) {
            targetWorkout.indexWorkout = listWorkouts[index - 1].indexWorkout + 1
        }

        ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("test1", "successfully updated a set")
            } else {
                Toast.makeText(requireContext(), "Couldn't update a set", Toast.LENGTH_SHORT).show()
            }
        }

        listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)

        rv_workout!!.adapter!!.notifyItemChanged(position)
    }

    override fun onModifySetModeButtonClick(
        deletionMode: Boolean,
        position: Int,
        indexExercise: Int
    ) {
        Log.d("test1", "onModifySet deletion mode called")

        filteredListWorkouts[selectedWorkoutIndex].workout[indexExercise].isEditMode = deletionMode

        val setSize = filteredListWorkouts[selectedWorkoutIndex].workout[indexExercise].sets.size

        listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)
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
        val set = filteredListWorkouts[selectedWorkoutIndex].workout[indexExercise].sets[indexSet]

        if (isModified) {
            set.isModified = true
            set.newWeight = newWeight
            set.newReps = newReps
        } else {
            set.isModified = false
        }

    }

    override fun onDeleteSetButtonClick(position: Int, indexExercise: Int, indexSet: Int) {
        Log.d("test1", "onDeleteSet called")

        var targetWorkout = filteredListWorkouts[selectedWorkoutIndex]

        targetWorkout.workout[indexExercise].sets.removeAt(indexSet)

        for (i in targetWorkout.workout[indexExercise].sets.indices) {
            targetWorkout.workout[indexExercise].sets[i].indexSet = i
        }

        ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("test1", "successfully deleted a set")
            } else {
                Toast.makeText(requireContext(), "Couldn't delete a set", Toast.LENGTH_SHORT).show()
            }
        }
        listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)

        rv_workout!!.adapter!!.notifyItemRemoved(position)
    }

    override fun onDeleteExerciseButtonClick(position: Int, indexExercise: Int) {
        Log.d("test1", "onDeleteExercise called on $indexExercise")

        val targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
        targetWorkout.workout.removeIf { it.indexExercise == indexExercise}

        for (i in indexExercise until filteredListWorkouts[selectedWorkoutIndex].workout.size) {
            targetWorkout.workout[i].indexExercise = i
            targetWorkout.workout[i].id = i + 1
        }

        ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("test1", "successfully deleted an exercise")
            } else {
                Toast.makeText(requireContext(), "Couldn't delete an exercise", Toast.LENGTH_SHORT).show()
            }
        }
        listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)

        rv_workout!!.adapter!!.notifyDataSetChanged()

    }

    override fun onAddExerciseButtonClick(position: Int) {
        Log.d("test1", "onAddExercise called")

        val targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
        targetWorkout.workout.add(Exercise(targetWorkout.workout.size + 1, "New Exercise", "Bodyweight", "", ArrayList<Set>(), true, targetWorkout.workout.size, false))
        Log.d("test1", "checkpoint 2")
        ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("test1", "successfully added an exercise")
            } else {
                Toast.makeText(requireContext(), "Couldn't add an exercise", Toast.LENGTH_SHORT).show()
            }
        }
        listAdapter!!.updateExercises(targetWorkout.workout)
        rv_workout!!.adapter!!.notifyItemInserted(position)
    }

    override fun onMoveExerciseButtonClick(position: Int, indexExercise: Int, direction: String) {
        val targetWorkout = filteredListWorkouts[selectedWorkoutIndex]
        val tempExercise = targetWorkout.workout[indexExercise]
        var result = 0
        var startPosition = 0
        var newIndex = 0
        if (direction.equals("Up")) {
            if (indexExercise > 0) {
                result = -1
                newIndex = targetWorkout.workout[indexExercise + result].indexExercise

                startPosition = position - targetWorkout.workout[newIndex].sets.size

            } else {
                Toast.makeText(requireContext(), "Unable to move up", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (indexExercise < targetWorkout.workout.size - 1) {
                result = 1
                newIndex = targetWorkout.workout[indexExercise + result].indexExercise

                startPosition = position
            } else {
                Toast.makeText(requireContext(), "Unable to move down", Toast.LENGTH_SHORT).show()
            }
        }

        if (result != 0) {

            val pastIndex = targetWorkout.workout[indexExercise].indexExercise

            targetWorkout.workout[newIndex].indexExercise = indexExercise
            targetWorkout.workout[pastIndex].indexExercise = indexExercise + result

            targetWorkout.workout[indexExercise] = targetWorkout.workout[indexExercise + result]
            targetWorkout.workout[indexExercise + result] = tempExercise


            val bothSetSize = targetWorkout.workout[indexExercise].sets.size + targetWorkout.workout[indexExercise + result].sets.size + 2
            rv_workout!!.adapter!!.notifyItemRangeChanged(startPosition, bothSetSize)

            ref!!.child(targetWorkout.id!!).setValue(targetWorkout).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("test1", "successfully moved an exercise")
                } else {
                    Toast.makeText(requireContext(), "Couldn't move an exercise", Toast.LENGTH_SHORT).show()
                }
            }

            listAdapter!!.updateExercises(filteredListWorkouts[selectedWorkoutIndex].workout)

            Log.d("test1", "startPosition: $startPosition for $bothSetSize items")
            rv_workout!!.adapter!!.notifyItemRangeChanged(startPosition, bothSetSize)

        }

    }

}