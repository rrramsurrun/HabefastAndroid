package com.ramsurrun.habefast.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [CompletedWorkout::class, TemplateWorkout::class, ExerciseTemplate::class, Exercise::class, ExerciseSet::class],
    version = 1,
    exportSchema = false
)
abstract class HabefastDatabase : RoomDatabase() {
    abstract fun completedWorkoutDetailsDao(): CompletedWorkoutDetailsDao
    abstract fun completedWorkoutDao(): CompletedWorkoutDao

    abstract fun exerciseTemplateDao(): ExerciseTemplateDao
    abstract fun exerciseDao(): ExerciseDao
    //Ensure only one instance running
abstract fun exerciseSetDao():ExerciseSetDao
    companion object {
        @Volatile
        private var Instance: HabefastDatabase? = null

        fun getDatabase(context: Context): HabefastDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HabefastDatabase::class.java, "habefast_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }

    }
}