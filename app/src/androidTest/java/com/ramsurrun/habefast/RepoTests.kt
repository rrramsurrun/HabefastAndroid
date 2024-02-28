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
import com.ramsurrun.habefast.data.WorkoutRepositoryImpl
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
class CompletedWorkoutDetailsRepoTest {
    private lateinit var habefastDatabase: HabefastDatabase
    private lateinit var completedWorkoutDao: CompletedWorkoutDao
    private lateinit var exerciseTemplateDao: ExerciseTemplateDao
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var exerciseSetDao: ExerciseSetDao
    private lateinit var exerciseDetailsDao: ExerciseDetailsDao
    private lateinit var completedWorkoutDetailsDao: CompletedWorkoutDetailsDao
    private lateinit var workoutRepositoryImpl: WorkoutRepositoryImpl

    private val completedWorkout1 = CompletedWorkout(1, "First workout", 69, "2023-09-01T10:15:30")
    private val exerciseTemplate1 =
        ExerciseTemplate(1, "Bench Press", ExerciseType.REPS, BodyPart.CHEST)
    private val exercise1 =
        Exercise(id = 1, workoutId = 1, exerciseTemplateId = 1, workoutTemplateId = null)
    private val exerciseSet1 = ExerciseSet(1, 1,50.00,10,null,null)
    private val exerciseSet2 = ExerciseSet(2, 1,55.00,10,null,null)
    private val exerciseSet3 = ExerciseSet(3, 1,60.00,10,null,null)
    private val exerciseDetails1 = ExerciseDetails(exercise1, listOf(exerciseSet1,exerciseSet2,exerciseSet3))
    private val completedWorkoutDetails1 = CompletedWorkoutDetails(completedWorkout1,listOf(exerciseDetails1))
    private val exerciseDetails1modified = ExerciseDetails(exercise1, listOf(exerciseSet1,exerciseSet2))
    private val completedWorkoutDetails1modified = CompletedWorkoutDetails(completedWorkout1,listOf(exerciseDetails1modified))


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
        workoutRepositoryImpl = WorkoutRepositoryImpl(completedWorkoutDao,exerciseTemplateDao,exerciseDao,exerciseSetDao,exerciseDetailsDao,completedWorkoutDetailsDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        println("Running closeDb")
        habefastDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoGet_CompleteWorkoutDetails() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        exerciseSetDao.insert(exerciseSet1)
        exerciseSetDao.insert(exerciseSet2)
        exerciseSetDao.insert(exerciseSet3)
        val allWorkoutDetails = workoutRepositoryImpl.getAllWorkoutsStream().first()
        assertEquals(allWorkoutDetails[0], completedWorkoutDetails1)
    }
    @Test
    @Throws(Exception::class)
    fun daoInsert_CompleteWorkoutDetails() = runBlocking {
        exerciseTemplateDao.insert(exerciseTemplate1)
        workoutRepositoryImpl.insertWorkout(completedWorkoutDetails1)
        val allWorkoutDetails = workoutRepositoryImpl.getAllWorkoutsStream().first()
        assertEquals(allWorkoutDetails[0], completedWorkoutDetails1)
    }
    @Test
    @Throws(Exception::class)
    fun daoDelete_CompleteWorkoutDetails() = runBlocking {
        exerciseTemplateDao.insert(exerciseTemplate1)
        workoutRepositoryImpl.insertWorkout(completedWorkoutDetails1)
        val allWorkoutDetails = workoutRepositoryImpl.getAllWorkoutsStream().first()
        assertEquals(allWorkoutDetails[0], completedWorkoutDetails1)
        workoutRepositoryImpl.deleteWorkout(completedWorkoutDetails1)
        val emptyWorkoutDetails = workoutRepositoryImpl.getAllWorkoutsStream().first()
        assertEmpty<CompletedWorkoutDetails>(emptyWorkoutDetails)
        val allExercises = exerciseDao.getAllExercises().first()
        assertEmpty<Exercise>(allExercises)
        val allExerciseSets = exerciseSetDao.getAllExerciseSets().first()
        assertEmpty<ExerciseSet>(allExerciseSets)
    }

    private fun <T> assertEmpty(list:List<T>){
        assertEquals(list,listOf<T>())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdate_CompleteWorkoutDetails() = runBlocking {
        exerciseTemplateDao.insert(exerciseTemplate1)
        workoutRepositoryImpl.insertWorkout(completedWorkoutDetails1)
        val allWorkoutDetails = workoutRepositoryImpl.getAllWorkoutsStream().first()
        assertEquals(allWorkoutDetails[0], completedWorkoutDetails1)

        workoutRepositoryImpl.updateWorkout(completedWorkoutDetails1modified)
        val modifiedWorkoutDetails = workoutRepositoryImpl.getAllWorkoutsStream().first()
        assertEquals(modifiedWorkoutDetails[0], completedWorkoutDetails1modified)
    }


}