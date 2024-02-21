package com.example.bottomanimationmydemo.model.course_filter_model

data class CourseFilterEntityResponse(
    val `data`: Data,
    val error: Error,
    val message: String,
    val status: Boolean
)

data class Data(
    val batch_goals: ArrayList<BatchGoal>,
    val batch_levels: ArrayList<BatchLevel>,
    val workout_types: ArrayList<WorkoutType>
)

data class BatchGoal(
    val goal_name: String,
    val id: Int,
    val status: Int
)

data class BatchLevel(
    val id: Int,
    val level_name: String,
    val status: Int
)

data class WorkoutType(
    val id: Int,
    val status: Int,
    val workout_type: String
)

class Error