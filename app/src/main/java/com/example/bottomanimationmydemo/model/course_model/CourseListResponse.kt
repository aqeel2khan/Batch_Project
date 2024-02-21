package com.example.bottomanimationmydemo.model.course_model


import com.google.gson.annotations.SerializedName

data class CourseListResponse(
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
    val list: ArrayList<ListData>
)

data class ListData (
    @SerializedName("coach_detail")
    val coachDetail: CoachDetail,
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

data class CourseLevel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("level_name")
    val levelName: String,
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