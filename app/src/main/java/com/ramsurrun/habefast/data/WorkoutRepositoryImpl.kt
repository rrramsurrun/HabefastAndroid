package com.ramsurrun.habefast.data

import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImpl(
    private val completedWorkoutDao: CompletedWorkoutDao,
    private val exerciseTemplateDao: ExerciseTemplateDao,
    private val exerciseDao: ExerciseDao,
    private val exerciseSetDao: ExerciseSetDao,
    private val exerciseDetailsDao: ExerciseDetailsDao,
    private val completedWorkoutDetailsDao: CompletedWorkoutDetailsDao
) : WorkoutRepository {
    override fun getAllWorkoutsStream(): Flow<List<CompletedWorkoutDetails>> {
        return completedWorkoutDetailsDao.getAllCompletedWorkoutDetails()
    }

    override suspend fun insertWorkout(workout: CompletedWorkoutDetails) {
        //Insert the workout
        completedWorkoutDao.insert(workout.completedWorkout)
        //Insert the exercises and sets
        insertExercises(workout.exercises)
    }

    override suspend fun deleteWorkout(workout: CompletedWorkoutDetails) {
        //Delete the workout
        completedWorkoutDao.delete(workout.completedWorkout.id)
    }

    override suspend fun updateWorkout(workout: CompletedWorkoutDetails) {
        //Update the workout
        completedWorkoutDao.update(workout.completedWorkout)
        //Delete the exercises
        deleteExercises(workout.exercises)
        //Insert the exercises
        insertExercises(workout.exercises)
    }

    private suspend fun insertExercises(exercises: List<ExerciseDetails>) {
        exercises.forEach {
            exerciseDao.insert(it.exercise)
            insertExerciseSets(it.exerciseSets)
        }
    }

    private suspend fun insertExerciseSets(sets: List<ExerciseSet>) {
        sets.forEach { exerciseSetDao.insert(it) }
    }
    private suspend fun deleteExercises(exercises: List<ExerciseDetails>) {
        exercises.forEach {
            exerciseDao.delete(it.exercise)
        }
    }
}