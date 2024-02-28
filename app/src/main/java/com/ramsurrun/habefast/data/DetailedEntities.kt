package com.ramsurrun.habefast.data

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


data class CompletedWorkoutDetails(
    @Embedded val completedWorkout: CompletedWorkout,
//    @Relation(entity=Exercise::class,parentColumn = "id", entityColumn = "workout_id")
//    val exercises:List<Exercise>
    @Relation(entity=Exercise::class,parentColumn = "id", entityColumn = "workoutId")
val exercises:List<ExerciseDetails>
)

data class ExerciseDetails(
    @Embedded val exercise:Exercise,
    @Relation(entity=ExerciseSet::class, parentColumn = "id", entityColumn = "exerciseId")
    val exerciseSets: List<ExerciseSet>
)

@Dao
interface CompletedWorkoutDetailsDao{

    @Transaction
    @Query("SELECT * FROM workout")
    fun getAllCompletedWorkoutDetails(): Flow<List<CompletedWorkoutDetails>>
}

@Dao
interface ExerciseDetailsDao{
    @Transaction
    @Query("SELECT * FROM exercise")
    fun getAllExerciseDetails(): Flow<List<ExerciseDetails>>

}