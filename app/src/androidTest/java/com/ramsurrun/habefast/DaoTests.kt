package com.ramsurrun.habefast

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ramsurrun.habefast.data.BodyPart
import com.ramsurrun.habefast.data.CompletedWorkout
import com.ramsurrun.habefast.data.CompletedWorkoutDao
import com.ramsurrun.habefast.data.CompletedWorkoutDetailsDao
import com.ramsurrun.habefast.data.Exercise
import com.ramsurrun.habefast.data.ExerciseDao
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

    private val completedWorkout1 = CompletedWorkout(1, "First workout", 69, "2023-09-01T10:15:30")
    private val exerciseTemplate1 =
        ExerciseTemplate(1, "Bench Press", ExerciseType.REPS, BodyPart.CHEST)
    private val exercise1 =
        Exercise(id = 1, workoutId = 1, exerciseTemplateId = 1, workoutTemplateId = null)
    private val exerciseSet1 = ExerciseSet(1, 1,50.00,10,null,null)
    private val exerciseSet2 = ExerciseSet(1, 1,55.00,10,null,null)
    private val exerciseSet3 = ExerciseSet(1, 1,60.00,10,null,null)

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
        assertEquals(allWorkouts[0], completedWorkout1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_ExerciseTemplate() = runBlocking {
        exerciseTemplateDao.insert(exerciseTemplate1)
        val allExerciseTemplates = exerciseTemplateDao.getAllExerciseTemplates().first()
        assertEquals(allExerciseTemplates[0], exerciseTemplate1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_Exercise() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        val allExercises = exerciseDao.getAllExercises().first()
        assertEquals(allExercises[0], exercise1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_ExerciseSet() = runBlocking {
        completedWorkoutDao.insert(completedWorkout1)
        exerciseTemplateDao.insert(exerciseTemplate1)
        exerciseDao.insert(exercise1)
        exerciseSetDao.insert(exerciseSet1)
        val allExerciseSets = exerciseSetDao.getAllExerciseSets().first()
        assertEquals(allExerciseSets[0], exerciseSet1)
    }

}