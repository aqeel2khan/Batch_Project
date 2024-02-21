package com.example.bottomanimationmydemo.model.course_workout_list


import com.google.gson.annotations.SerializedName

data class CourseWorkoutListResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Error,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<ExerciseList>
)

data class ExerciseList (
    @SerializedName("calorie_burn")
    val calorieBurn: String,
    @SerializedName("course_duration_exercise")
    val courseDurationExercise: List<CourseDurationExercise>,
    @SerializedName("course_duration_id")
    val courseDurationId: Int,
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("day_name")
    val dayName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("no_of_exercise")
    val noOfExercise: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("workout_time")
    val workoutTime: String
)

data class CourseDurationExercise(
    @SerializedName("course_duration_exercise_id")
    val courseDurationExerciseId: Int,
    @SerializedName("course_duration_id")
    val courseDurationId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("exercise_set")
    val exerciseSet: Any,
    @SerializedName("exercise_time")
    val exerciseTime: Any,
    @SerializedName("exercise_wraps")
    val exerciseWraps: Any,
    @SerializedName("instruction")
    val instruction: Any,
    @SerializedName("title")
    val title: Any,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_detail")
    val videoDetail: Any,
    @SerializedName("video_id")
    val videoId: Any
)

class Error