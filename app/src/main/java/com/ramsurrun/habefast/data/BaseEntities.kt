package com.ramsurrun.habefast.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

interface Workout {
    val id: Int?
    val name: String
    //val exercises: List<Exercises>
}

@Entity(tableName = "workout")
data class CompletedWorkout(
    @PrimaryKey(autoGenerate = true)
    override val id: Int? = 0,
    override val name: String,
    val durationMinutes: Int,
    val startTime: String
) : Workout

@Entity(tableName = "workoutTemplate")
data class TemplateWorkout(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val name: String,
) : Workout

@Entity(tableName = "exerciseTemplate")
data class ExerciseTemplate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val exerciseType: ExerciseType,
    val bodyPart: BodyPart
)

enum class BodyPart {
    CHEST, BACK, QUADS, HAMSTRINGS, GLUTES
}

enum class ExerciseType {
    DISTANCE, DURATION, REPS
}

@Entity(
    tableName = "exercise",
    indices = [Index(value = ["workoutId", "workoutTemplateId", "exerciseTemplateId"])],

    foreignKeys = [
        ForeignKey(
            entity = CompletedWorkout::class,
            parentColumns = ["id"], childColumns = ["workoutId"], onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TemplateWorkout::class,
            parentColumns = ["id"],
            childColumns = ["workoutTemplateId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseTemplate::class,
            parentColumns = ["id"],
            childColumns = ["exerciseTemplateId"],
            onDelete = ForeignKey.RESTRICT
        )]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workoutId: Int?,
    val workoutTemplateId: Int?,
    val exerciseTemplateId: Int
)

@Entity(
    tableName = "exerciseNote", indices = [Index(value = ["exerciseId"])], foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"], childColumns = ["exerciseId"], onDelete = ForeignKey.CASCADE
        )]
)
data class ExerciseNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseId: Int,
    val sortOrder: Int,
    val note: String
)

@Entity(
    tableName = "exerciseSet", indices = [Index(value = ["exerciseId"])], foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseId: Int,
    val weightKg: Double?,
    val reps: Int?,
    val durationSeconds: Int?,
    val distanceKm: Double?
)

