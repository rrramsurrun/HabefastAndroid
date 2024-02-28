package com.ramsurrun.habefast.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class CompletedWorkout(

    val name: String,
    val durationMinutes: Int,
    val startTime: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(tableName = "workoutTemplate")
data class TemplateWorkout(
     val name: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(tableName = "exerciseTemplate")
data class ExerciseTemplate(
    val name: String,
    val exerciseType: ExerciseType,
    val bodyPart: BodyPart
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

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
    val workoutId: Int?,
    val workoutTemplateId: Int?,
    val exerciseTemplateId: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(
    tableName = "exerciseNote", indices = [Index(value = ["exerciseId"])], foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"], childColumns = ["exerciseId"], onDelete = ForeignKey.CASCADE
        )]
)
data class ExerciseNote(
    val exerciseId: Int,
    val sortOrder: Int,
    val note: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

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
    val exerciseId: Int,
    val weightKg: Double?,
    val reps: Int?,
    val durationSeconds: Int?,
    val distanceKm: Double?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

