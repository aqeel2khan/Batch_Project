package com.example.bottomanimationmydemo.model.courseorderlist

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CourseOrderList (
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

    @SerializedName("list") val list: ArrayList<OrderList>,
    @SerializedName("count") val count: Int
)
data class OrderList(

    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("course_id") val course_id: Int,
    @SerializedName("subtotal") val subtotal: Double,
    @SerializedName("discount") val discount: Double,
    @SerializedName("tax") val tax: Double,
    @SerializedName("total") val total: Double,
    @SerializedName("payment_type") val payment_type: String,
    @SerializedName("transaction_id") val transaction_id: Int,
    @SerializedName("payment_status") val payment_status: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("course_detail") val course_detail: Course_detail
)

data class Course_detail(

    @SerializedName("course_id") val course_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("course_name") val course_name: String,
    @SerializedName("course_image") val course_image: String,
    @SerializedName("course_promo_video") val course_promo_video: Int,
    @SerializedName("course_repetition") val course_repetition: String,
    @SerializedName("course_validity") val course_validity: Int,
    @SerializedName("description") val description: String,
    @SerializedName("per_day_workout") val per_day_workout: Int,
    @SerializedName("weight_required") val weight_required: String,
    @SerializedName("course_price") val course_price: Double,
    @SerializedName("discount_price") val discount_price: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("course_level") val course_level: Course_level,
    @SerializedName("status") val status: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("coach_detail") val coach_detail: Coach_detail,
    @SerializedName("course_duration") val course_duration: ArrayList<Course_duration>,
    @SerializedName("goals") val goals:ArrayList<Goals>,
    @SerializedName("workout_type") val workout_type: ArrayList<Workout_type>
)

data class Course_level(

    @SerializedName("id") val id: Int,
    @SerializedName("level_name") val level_name: String,
    @SerializedName("status") val status: Int
)

data class Coach_detail(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_photo_path") val profile_photo_path: String
)

data class Course_duration_exercise(

    @SerializedName("course_duration_exercise_id") val course_duration_exercise_id: Int,
    @SerializedName("course_duration_id") val course_duration_id: Int,
    @SerializedName("video_id") val video_id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("instruction") val instruction: String,
    @SerializedName("exercise_set") val exercise_set: Int,
    @SerializedName("exercise_wraps") val exercise_wraps: Int,
    @SerializedName("exercise_time") val exercise_time: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("video_detail") val video_detail: Video_detail
)

data class Video_detail(

    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("folder_id") val folder_id: Int,
    @SerializedName("video_title") val video_title: String,
    @SerializedName("video_description") val video_description: String,
    @SerializedName("video_id") val video_id: Int,
    @SerializedName("duration") val duration: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("player_embed_url") val player_embed_url: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Goals(

    @SerializedName("id") val id: Int,
    @SerializedName("course_id") val course_id: Int,
    @SerializedName("goal_id") val goal_id: Int,
    @SerializedName("batchgoal") val batchgoal: kotlin.collections.List<Batchgoal>
)

data class Course_duration(

    @SerializedName("course_duration_id") val course_duration_id: Int,
    @SerializedName("course_id") val course_id: Int,
    @SerializedName("day_name") val day_name: String,
    @SerializedName("description") val description: String,
    @SerializedName("no_of_exercise") val no_of_exercise: Int,
    @SerializedName("calorie_burn") val calorie_burn: Int,
    @SerializedName("workout_time") val workout_time: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("course_duration_exercise") val course_duration_exercise: kotlin.collections.List<Course_duration_exercise>
)

data class Batchgoal(

    @SerializedName("id") val id: Int,
    @SerializedName("goal_name") val goal_name: String,
    @SerializedName("status") val status: Int
)

data class Workout_type(

    @SerializedName("id") val id: Int,
    @SerializedName("course_id") val course_id: Int,
    @SerializedName("workout_type_id") val workout_type_id: Int,
    @SerializedName("workoutdetail") val workoutdetail: Workoutdetail
)

data class Workoutdetail(

    @SerializedName("id") val id: Int,
    @SerializedName("workout_type") val workout_type: String
)
