package com.ramsurrun.habefast

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ramsurrun.habefast.data.BodyPart
import com.ramsurrun.habefast.data.CompletedWorkout
import com.ramsurrun.habefast.data.CompletedWorkoutDao
import com.ramsurrun.habefast.data.CompletedWorkoutDetails
import com.ramsurrun.habefast.data.CompletedWorkoutDetailsDao
import com.ramsurrun.habefast.data.Exercise
import com.ramsurrun.habefast.data.ExerciseDao
import com.ramsurrun.habefast.data.ExerciseDetails
import com.ramsurrun.habefast.data.ExerciseDetailsDao
import com.ramsurrun.habefast.data.ExerciseSet
import com.ramsurrun.habefast.data.ExerciseSetDao
import com.ramsurrun.habefast.data.ExerciseTemplate
import com.ramsurrun.habefast.data.ExerciseTemplateDao
import com.ramsurrun.habefast.data.ExerciseType
import com.ramsurrun.habefast.data.HabefastDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CompletedWorkoutDetailsDaoTest {
    private lateinit var habefastDatabase: HabefastDatabase
    private lateinit var completedWorkoutDao: CompletedWorkoutDao
    private lateinit var exerciseTemplateDao: ExerciseTemplateDao
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var exerciseSetDao: ExerciseSetDao
    private lateinit var exerciseDetailsDao: ExerciseDetailsDao
    private lateinit var completedWorkoutDetailsDao: CompletedWorkoutDetailsDao

    private val completedWorkout1 = CompletedWorkout( "First workout", 69, "2023-09-01T10:15:30")
    private val exerciseTemplate1 =
        ExerciseTemplate( "Bench Press", ExerciseType.REPS, BodyPart.CHEST)
    private val exercise1 =
        Exercise(workoutId = 1, exerciseTemplateId = 1, workoutTemplateId = null)
    private val exerciseSet1 = ExerciseSet(1,50.00,10,null,null)
    private val exerciseDetails1 = ExerciseDetails(exercise1, listOf(exerciseSet1))
    private val completedWorkoutDetails1 = CompletedWorkoutDetails(completedWorkout1,listOf(exerciseDetails1))

    @Before
    fun createDbForTest() {
        println("Running createDbForTest")
        val context: Context = ApplicationProvider.getApplicationContext()
        habefastDatabase = Room.inMemoryDatabaseBuilder(context, HabefastDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        completedWorkoutDao = habefastDatabase.completedWorkoutDao()
        exerciseTemplateDao = habefastDatabase.exerciseTemplateDao()
        exerciseDao = habefastDatabase.exerciseDao()
        exerciseSetDao = habefastDatabase.exerciseSetDao()
        exerciseDetailsDao = habefastDatabase.exerciseDetailsDao()
        completedWorkoutDetailsDao = habefastDatabase.completedWorkoutDetailsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        println("Running closeDb")
        habefastDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_CompleteWorkout() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        val allWorkouts = completedWorkoutDao.getAllCompletedWorkouts().first()
        assertEquals(completedWorkout1,allWorkouts[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_ExerciseTemplate() = runBlocking {
        exerciseTemplateDao.insert(exerciseTemplate1)
        val allExerciseTemplates = exerciseTemplateDao.getAllExerciseTemplates().first()
        assertEquals(exerciseTemplate1,allExerciseTemplates[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_Exercise() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        val allExercises = exerciseDao.getAllExercises().first()
        assertEquals(exercise1,allExercises[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_ExerciseSet() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        exerciseSetDao.insert(exerciseSet1)
        val allExerciseSets = exerciseSetDao.getAllExerciseSets().first()
        assertEquals(exerciseSet1,allExerciseSets[0])
    }
    @Test
    @Throws(Exception::class)
    fun daoGet_ExerciseDetails() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        exerciseSetDao.insert(exerciseSet1)
        val allExerciseDetails = exerciseDetailsDao.getAllExerciseDetails().first()
        assertEquals(exerciseDetails1,allExerciseDetails[0])
    }
    @Test
    @Throws(Exception::class)
    fun daoGet_WorkoutDetails() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        exerciseSetDao.insert(exerciseSet1)
        val allWorkoutDetails = completedWorkoutDetailsDao.getAllCompletedWorkoutDetails().first()
        assertEquals(completedWorkoutDetails1,allWorkoutDetails[0])
    }

//    @Test
//    @Throws(Exception::class)
//    fun daoInsert_WorkoutDetails() = runBlocking {
//        completedWorkoutDetailsDao.insert(completedWorkoutDetails1)
//        val allWorkoutDetails = completedWorkoutDetailsDao.getAllCompletedWorkoutDetails().first()
//        assertEquals(allWorkoutDetails[0], completedWorkoutDetails1)
//    }

}