package com.ramsurrun.habefast.data

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

data class CompletedWorkoutDetails(
    @Embedded val completedWorkout: CompletedWorkout,
//    @Relation(entity=Exercise::class,parentColumn = "id", entityColumn = "workout_id")
//    val exercises:List<Exercise>
    @Relation(entity=ExerciseDetails::class,parentColumn = "id", entityColumn = "workout_id")
val exercises:List<Exercise>
)
data class ExerciseDetails(
    @Embedded val exercise:Exercise,
    @Relation(entity=ExerciseSet::class, parentColumn = "id", entityColumn = "exercise_id")
    val exerciseSets: List<ExerciseSet>
)

@Dao
interface CompletedWorkoutDetailsDao{

    @Query("SELECT * FROM workout")
    fun getAllItems(): Flow<List<CompletedWorkout>>
}