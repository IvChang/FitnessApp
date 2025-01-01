package com.example.fitnessapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.fragments.OnItemInteractionListener
import com.example.fitnessapp.objects.Exercise
import com.example.fitnessapp.objects.Workout

class ListAdapter(
    var context: Context,
    var groups: List<Workout>,
    private val listener: OnItemInteractionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var indexSets = 0

    // Décide et crée le holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == VIEW_TYPE_EXERCISE) {
            view =
                (LayoutInflater.from(context).inflate(R.layout.exercisegroup_view, parent, false))
            return WorkoutHolder(view, listener)
        } else if (viewType == VIEW_TYPE_ADDSET) {
            view = (LayoutInflater.from(context).inflate(R.layout.add_set_view, parent, false))
            return AddSetHolder(view, listener)
        } else if (viewType == VIEW_TYPE_SET) {
            view = (LayoutInflater.from(context).inflate(R.layout.set_view, parent, false))
            return SetHolder(view, listener)
        } else {
            view = (LayoutInflater.from(context).inflate(R.layout.add_exercise_view, parent, false))
            return AddExerciseHolder(view, listener)
        }
    }

    //Pour modifier les widgets d'un setHolder à partir d'une autre classe en rafraichissant certaines positions
    // dans le RecyclerList, appellant onBindViewHolder
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (!payloads.isEmpty()) {
            for (payload in payloads) {
                if (holder.itemViewType == VIEW_TYPE_SET) {
                    val setHolder = holder as SetHolder
                    if (payload == "deletionMode") {
                        setHolder.updateImageView(true)
                    } else if (payload == "normalMode") {
                        setHolder.updateImageView(false)
                    }
                    val exercise = getExerciseForPosition(position)
                    indexSets = getSetIndexForPosition(position)
                    val set = exercise!!.sets[indexSets]
                    setHolder.setSet(set, exercise.indexExercise, exercise.isEditMode)
                }
            }
        } else {
            // Appel l'autre onBindViewHolder
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    // Modifier le holder et ses éléments
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val exercise = getExerciseForPosition(position)
        if (holder.itemViewType == VIEW_TYPE_EXERCISE) {
            val exerciseHolder = holder as WorkoutHolder
            exerciseHolder.setIndexExercise(exercise!!.indexExercise)

            exerciseHolder.actv_name.setText(exercise.name)
            exerciseHolder.tv_sets.text = exercise.sets.size.toString() + " SETS"
            exerciseHolder.bindWorkout(exercise)
        } else if (holder.itemViewType == VIEW_TYPE_ADDSET) {
            val addSetHolder = holder as AddSetHolder
            addSetHolder.setIndexExercise(exercise!!.indexExercise)

            if (exercise != null && exercise.setsAreVisible) {
                val params = addSetHolder.itemView.layoutParams
                params.height = 120
                addSetHolder.itemView.layoutParams = params
            } else {
                val params = addSetHolder.itemView.layoutParams
                params.height = 0
                addSetHolder.itemView.layoutParams = params
            }
        } else if (holder.itemViewType == VIEW_TYPE_SET) {
            if (exercise != null) {
                val setHolder = holder as SetHolder

                indexSets = getSetIndexForPosition(position)
                val set = exercise.sets[indexSets]
                setHolder.setSet(set, exercise.indexExercise, exercise.isEditMode)

                setHolder.tv_setNo.text = (indexSets + 1).toString()
                //Log.d("test1", "set " + set.indexSet + " is " + set.isModified)

                if (exercise.isEditMode) {
                    setHolder.iv_modifySet.visibility = View.VISIBLE
                } else {
                    setHolder.iv_modifySet.visibility = View.GONE
                }

                if (set.isModified) {
                    setHolder.iv_modifySet.visibility = View.VISIBLE
                    var newWeight = set.newWeight.toString() // pour une certaine raison, je dois faire ca pour que ca fonctionne
                    setHolder.et_reps.setText(set.newReps.toString())
                    setHolder.et_weight.setText(newWeight)

                } else {
                    setHolder.et_weight.setText(set.weight.toString())
                    setHolder.et_reps.setText(set.reps.toString())
                    if (!exercise.isEditMode) {
                        setHolder.iv_modifySet.visibility = View.GONE
                    }

                }



                if (set.isVisible) {
                    val params = setHolder.itemView.layoutParams
                    params.height = 100
                    setHolder.itemView.layoutParams = params
                    exercise.setsAreVisible = true
                } else {
                    val params = setHolder.itemView.layoutParams
                    params.height = 0
                    setHolder.itemView.layoutParams = params
                    exercise.setsAreVisible = false
                }
            }
        }
    }

    //Trouve l'exercise correspondant en parcourant la liste d'exercices en meme temps de vérifier à
    // chaque fois si la position de l'exercice qu'on parcoure correspond à la position entrée
    private fun getExerciseForPosition(position: Int): Exercise? {
        var index = 0
        for (exercise in groups[0].workout) {
            if (position >= index && position <= (index + exercise.sets.size + 1)) { // +1 pour prendre en compte le button addSet
                return exercise
            }
            index += exercise.sets.size + 2
        }
        return null
    }


    //Determine l'index du set en comparant la position entrée à chacun des positions qui délimite
    //chaque exercice dans le but de trouver dans quel exercice le set recherché se trouve, puis
    // on soustrait la position entrée de l'index délimitant le bon exercice pour trouver l'index pointant
    // le set de l'exercice.
    private fun getSetIndexForPosition(position: Int): Int {
        var index = 0
        for (exercise in groups[0].workout) {
            val endIndex = index + exercise.sets.size

            if (position > index && position <= endIndex) {
                return position - index - 1 //-1 pour exclure l'exercice
            }
            index += exercise.sets.size + 2
        }
        return -1
    }

    // Determine quel type (exercise, set ou addSet) afficher dans recyclerView
    override fun getItemViewType(position: Int): Int {
        return if (isExercise(position)) {
            VIEW_TYPE_EXERCISE
        } else if (isEndOfSets(position)) {
            VIEW_TYPE_ADDSET
        } else if (isEndOfList(position)) {
            VIEW_TYPE_ADDEXERCISE
        } else {
            VIEW_TYPE_SET
        }
    }

    // Vérifie si l'élément est un exercice en déterminant toutes les positions qui sont un exercice
    // et vérifier si la position actuelle correspond à l'une de celles-ci
    private fun isExercise(position: Int): Boolean {
        var index = 0
        for (exercise in groups[0].workout) {
            if (position == index) {
                return true
            }
            index += if (position == 1) {
                exercise.sets.size + 1
            } else {
                exercise.sets.size + 2
            }
        }
        return false
    }

    // Vérifie si l'élément est à la fin d'un set en déterminant toutes les positions qui sont une fin de sets
    // et vérifier si la position actuelle correspond à l'une de celles-ci
    private fun isEndOfSets(position: Int): Boolean {
        var index = 0
        for (exercise in groups[0].workout) {
            index += if (index == 0) {
                exercise.sets.size + 1
            } else {
                exercise.sets.size + 2
            }

            if (position == index) {
                return true
            }
        }
        return false
    }

    private fun isEndOfList(position: Int): Boolean {
        var index = 0
        var isEndOfList = false
        for (exercise in groups[0].workout) {
            index += if (index == 0) {
                exercise.sets.size + 1
            } else {
                exercise.sets.size + 2
            }
        }
        //Log.d("test1", "index : " + index); // the current last position (last add set) is position 32
        if (position == index + 1) {
            isEndOfList = true
        }
        return isEndOfList
    }


    fun updateImageView(position: Int, setSize: Int, deletionMode: Boolean) {
        if (deletionMode){
            notifyItemRangeChanged(position + 1, setSize, "deletionMode")
        } else {
            notifyItemRangeChanged(position + 1, setSize, "normalMode")
        }
    }


    override fun getItemCount(): Int {
        var nbSets = 0
        for (i in groups[0].workout.indices) {
            nbSets += groups[0].workout[i].sets.size + 1
        }
        return groups[0].workout.size + nbSets + 1
    }


    companion object {
        const val VIEW_TYPE_EXERCISE = 0
        const val VIEW_TYPE_SET = 1
        const val VIEW_TYPE_ADDSET = 2
        const val VIEW_TYPE_ADDEXERCISE = 3
    }
}
