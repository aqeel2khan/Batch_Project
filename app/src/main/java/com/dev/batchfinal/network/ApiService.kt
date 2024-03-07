package com.dev.batchfinal.network

import com.dev.batchfinal.model.chosen_meal_details_model.ChosenMealDetailsResponse
import com.dev.batchfinal.model.coach_detail_model.CoachDetailResponse
import com.dev.batchfinal.model.coach_filter_list.CoachFilterListResponse
import com.dev.batchfinal.model.coach_list_model.CoachListResponse
import com.dev.batchfinal.model.course_detail.CourseDetailResponse
import com.dev.batchfinal.model.course_filter_model.CourseFilterEntityResponse
import com.dev.batchfinal.model.course_model.CourseListResponse
import com.dev.batchfinal.model.course_promocode.CousePromocodeResponse
import com.dev.batchfinal.model.course_workout_list.CourseWorkoutListResponse
import com.dev.batchfinal.model.courseorderlist.CourseOrderList
import com.dev.batchfinal.model.login_model.LoginResponseModel
import com.dev.batchfinal.model.meal_detail_model.MealDetailResponse
import com.dev.batchfinal.model.meal_dish_model.MealDishResponse
import com.dev.batchfinal.model.meal_filter_model.MealFilterResponse
import com.dev.batchfinal.model.meal_list.MealResponseList
import com.dev.batchfinal.model.meal_plan_subscribe.MealSubscribedRequest
import com.dev.batchfinal.model.meal_plan_subscribe.MealsSubscribedRespnse
import com.dev.batchfinal.model.order_model.OrederCreateResponse
import com.dev.batchfinal.model.registeration_model.SignUpResponseModel
import com.dev.batchfinal.model.search_curse_filter.SearchCourseListByFilterResponse
import com.dev.batchfinal.utils.MyConstant.AUTHORIZATION
import com.dev.batchfinal.utils.MyConstant.CHOSEN_MEAL_DETAILS
import com.dev.batchfinal.utils.MyConstant.COACH
import com.dev.batchfinal.utils.MyConstant.COACHDETAIL
import com.dev.batchfinal.utils.MyConstant.COACHFILTERENTITY
import com.dev.batchfinal.utils.MyConstant.COURSE
import com.dev.batchfinal.utils.MyConstant.COURSEDETAIL
import com.dev.batchfinal.utils.MyConstant.COURSEFILTERENTITY
import com.dev.batchfinal.utils.MyConstant.COURSEORDERLIST
import com.dev.batchfinal.utils.MyConstant.COURSEPROMOCODELIST
import com.dev.batchfinal.utils.MyConstant.COURSEWORKOUTLIST
import com.dev.batchfinal.utils.MyConstant.LOGIN
import com.dev.batchfinal.utils.MyConstant.MEALDETAILS
import com.dev.batchfinal.utils.MyConstant.MEALDISH
import com.dev.batchfinal.utils.MyConstant.MEALFILTER
import com.dev.batchfinal.utils.MyConstant.MEALLIST
import com.dev.batchfinal.utils.MyConstant.MEAL_SUBSCRIBE
import com.dev.batchfinal.utils.MyConstant.ORDERCREATE
import com.dev.batchfinal.utils.MyConstant.SIGNUP
import com.dev.batchfinal.utils.MyConstant.STARTWORKOUTSTATUS
import com.google.gson.JsonObject
import retrofit2.http.*


interface ApiService {
    @POST(LOGIN)
    suspend fun loginApi(@Body jsonObject: JsonObject): LoginResponseModel

    @POST(SIGNUP)
    suspend fun signUpApi(@Body jsonObject: JsonObject): SignUpResponseModel

    @POST(COACH)
    suspend fun coachList(): CoachListResponse
    @POST(COACH)
    suspend fun searchCoachList(@Body jsonObject: JsonObject): CoachListResponse

    @GET(COACHDETAIL)
    suspend fun coachDetail(@Path("id") id: Int): CoachDetailResponse

    @POST(COURSE)
    suspend fun courseList(): CourseListResponse

    @GET(COURSEDETAIL)
    suspend fun courseDetail(@Path("id") id: String): CourseDetailResponse

    @GET(COURSEWORKOUTLIST)
    suspend fun courseWorkoutList(@Path("id") id: String): CourseWorkoutListResponse

    @POST(COURSE)
    suspend fun coachCourseList(@Body jsonObject: JsonObject): CourseListResponse

    @GET(COURSEFILTERENTITY)
    suspend fun courseFilterEntity(): CourseFilterEntityResponse

    @GET(COURSEPROMOCODELIST)
    suspend fun coursePromoCode(): CousePromocodeResponse

    @GET(COACHFILTERENTITY)
    suspend fun coachFilterEntity(): CoachFilterListResponse

    @POST(ORDERCREATE)
    suspend fun courseOrderCreate(@Header(AUTHORIZATION) token: String, @Body jsonObject: JsonObject): OrederCreateResponse

    @POST(COURSEORDERLIST)
    suspend fun courseOrderList(@Header(AUTHORIZATION) token: String): CourseOrderList

    @POST(STARTWORKOUTSTATUS)
    suspend fun startWorkout(@Header(AUTHORIZATION) token: String, @Body jsonObject: JsonObject): OrederCreateResponse

    @POST(COURSE)
    suspend fun searchCourseListByFilter(@Body jsonObject: JsonObject): SearchCourseListByFilterResponse

    @POST(MEALLIST)
    suspend fun getMealList(): MealResponseList

    @GET(MEALDETAILS)
    suspend fun mealDetails(@Path("id") id: String): MealDetailResponse

    @POST(MEALFILTER)
    suspend fun mealFilter(): MealFilterResponse

    @GET(MEALDISH)
    suspend fun mealDishData(@Path("id") id: Int): MealDishResponse

    @POST(CHOSEN_MEAL_DETAILS)
    suspend fun getDishDetails(@Body jsonObject: JsonObject): ChosenMealDetailsResponse

    @POST(MEAL_SUBSCRIBE)
    suspend fun mealSubscribe(@Body mealSubscribedRequest: MealSubscribedRequest): MealsSubscribedRespnse
}