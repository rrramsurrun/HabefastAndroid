package com.ramsurrun.habefast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface CompletedWorkoutDao {

    @Query("SELECT * from workout")
    fun getAllCompletedWorkouts(): Flow<List<CompletedWorkout>>

    @Query("SELECT * from workout WHERE id = :id")
    fun getCompletedWorkout(id: Int): Flow<CompletedWorkout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CompletedWorkout)

    @Update
    suspend fun update(item: CompletedWorkout)

    @Query("DELETE FROM workout WHERE id=:id")
    suspend fun delete(id: Int)
}
@Dao
interface ExerciseTemplateDao {

    @Query("SELECT * from exerciseTemplate")
    fun getAllExerciseTemplates(): Flow<List<ExerciseTemplate>>

    @Query("SELECT * from exerciseTemplate WHERE id = :id")
    fun getExerciseTemplate(id: Int): Flow<ExerciseTemplate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExerciseTemplate)

    @Update
    suspend fun update(item: ExerciseTemplate)

    @Delete
    suspend fun delete(item: ExerciseTemplate)
}
@Dao
interface ExerciseDao {

    @Query("SELECT * from exercise")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * from exercise WHERE id = :id")
    fun getExercise(id: Int): Flow<Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Exercise)

    @Update
    suspend fun update(item: Exercise)

    @Delete
    suspend fun delete(item: Exercise)
}
@Dao
interface ExerciseSetDao {

    @Query("SELECT * from exerciseSet")
    fun getAllExerciseSets(): Flow<List<ExerciseSet>>

    @Query("SELECT * from exerciseSet WHERE id = :id")
    fun getExerciseSet(id: Int): Flow<ExerciseSet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExerciseSet)

    @Update
    suspend fun update(item: ExerciseSet)

    @Delete
    suspend fun delete(item: ExerciseSet)
}