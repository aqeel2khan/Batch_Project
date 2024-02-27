package com.example.bottomanimationmydemo.model.course_detail


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseDetailResponse(
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
    @SerializedName("coach_detail")
    val coachDetail: CoachDetail,
    @SerializedName("course_duration")
    val courseDuration: List<CourseDuration?>,
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("course_image")
    val courseImage: String,
    @SerializedName("course_level")
    val courseLevel: CourseLevel,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("course_price")
    val coursePrice: String,
    @SerializedName("course_promo_video")
    val coursePromoVideo: String,
    @SerializedName("course_repetition")
    val courseRepetition: String,
    @SerializedName("course_validity")
    val courseValidity: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount_price")
    val discountPrice: Any,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("goals")
    val goals: List<Goal>,
    @SerializedName("per_day_workout")
    val perDayWorkout: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("weight_required")
    val weightRequired: String,
    @SerializedName("workout_type")
    val workoutType: List<WorkoutType>
)
data class CoachDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_photo_path")
    val profilePhotoPath: String
)

data class CourseDuration(
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
    val description: String,
    @SerializedName("exercise_set")
    val exerciseSet: String,
    @SerializedName("exercise_time")
    val exerciseTime: String,
    @SerializedName("exercise_wraps")
    val exerciseWraps: String,
    @SerializedName("instruction")
    val instruction: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_detail")
    val videoDetail: Any,
    @SerializedName("video_id")
    val videoId: Any
)

data class CourseLevel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("level_name")
    val levelName: String,
    @SerializedName("status")
    val status: Int
)

data class Goal(
    @SerializedName("batchgoal")
    val batchgoal: List<Batchgoal>,
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("goal_id")
    val goalId: Int,
    @SerializedName("id")
    val id: Int
)

data class Batchgoal(
    @SerializedName("goal_name")
    val goalName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: Int
)

data class WorkoutType(
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("workout_type_id")
    val workoutTypeId: Int,
    @SerializedName("workoutdetail")
    val workoutdetail: Workoutdetail
)

data class Workoutdetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("workout_type")
    val workoutType: String
)

class Error