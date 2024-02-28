package com.ramsurrun.habefast.data

import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    //Retrieve all workouts
    fun getAllWorkoutsStream(): Flow<List<CompletedWorkoutDetails>>
    //Insert workout
    suspend fun insertWorkout(workout: CompletedWorkoutDetails)
    //Delete Workout
    suspend fun deleteWorkout(workout: CompletedWorkoutDetails)
    //Update Workout
    suspend fun updateWorkout(workout: CompletedWorkoutDetails)
}