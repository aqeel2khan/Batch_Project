package com.example.bottomanimationmydemo.network

import com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_plan_subscribe.MealSubscribedRequest
import com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_plan_subscribe.MealsSubscribedRespnse
import com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_plan_subscription_update.MealPlanSubscriptionUpdateResponse
import com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_subscription_details_model.MealSubscriptionDetailsRequest
import com.example.bottomanimationmydemo.meals.meal_purchase.model.subscribe_list_model.MealSubscribeListRequest
import com.example.bottomanimationmydemo.meals.meal_purchase.model.subscribe_list_model.MealSubscribeListResponse
import com.example.bottomanimationmydemo.meals.meal_unpurchase.model.chosen_meal_details_model.ChosenMealDetailsResponse
import com.example.bottomanimationmydemo.meals.meal_unpurchase.model.subscribe.CheckSubscribeModel
import com.example.bottomanimationmydemo.model.coach_detail_model.CoachDetailResponse
import com.example.bottomanimationmydemo.model.coach_filter_list.CoachFilterListResponse
import com.example.bottomanimationmydemo.model.coach_list_model.CoachListResponse
import com.example.bottomanimationmydemo.model.course_detail.CourseDetailResponse
import com.example.bottomanimationmydemo.model.course_filter_model.CourseFilterEntityResponse
import com.example.bottomanimationmydemo.model.course_model.CourseListResponse
import com.example.bottomanimationmydemo.model.course_promocode.CousePromocodeResponse
import com.example.bottomanimationmydemo.model.course_workout_list.CourseWorkoutListResponse
import com.example.bottomanimationmydemo.model.courseorderlist.CourseOrderList
import com.example.bottomanimationmydemo.model.login_model.LoginResponseModel
import com.example.bottomanimationmydemo.model.meal_detail_model.MealDetailResponse
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishResponse
import com.example.bottomanimationmydemo.model.meal_filter_model.MealFilterResponse
import com.example.bottomanimationmydemo.model.meal_list.MealResponseList


import com.example.bottomanimationmydemo.model.order_model.OrederCreateResponse
import com.example.bottomanimationmydemo.model.registeration_model.SignUpResponseModel
import com.example.bottomanimationmydemo.model.search_curse_filter.SearchCourseListByFilterResponse

import com.example.bottomanimationmydemo.utils.MyConstant.AUTHORIZATION
import com.example.bottomanimationmydemo.utils.MyConstant.CHOSEN_MEAL_DETAILS
import com.example.bottomanimationmydemo.utils.MyConstant.COACH
import com.example.bottomanimationmydemo.utils.MyConstant.COACHDETAIL
import com.example.bottomanimationmydemo.utils.MyConstant.COACHFILTERENTITY
import com.example.bottomanimationmydemo.utils.MyConstant.COURSE
import com.example.bottomanimationmydemo.utils.MyConstant.COURSEDETAIL
import com.example.bottomanimationmydemo.utils.MyConstant.COURSEFILTERENTITY
import com.example.bottomanimationmydemo.utils.MyConstant.COURSEORDERLIST
import com.example.bottomanimationmydemo.utils.MyConstant.COURSEPROMOCODELIST
import com.example.bottomanimationmydemo.utils.MyConstant.COURSEWORKOUTLIST
import com.example.bottomanimationmydemo.utils.MyConstant.LOGIN
import com.example.bottomanimationmydemo.utils.MyConstant.MEALDETAILS
import com.example.bottomanimationmydemo.utils.MyConstant.MEALDISH
import com.example.bottomanimationmydemo.utils.MyConstant.MEALFILTER
import com.example.bottomanimationmydemo.utils.MyConstant.MEALLIST
import com.example.bottomanimationmydemo.utils.MyConstant.MEAL_SUBSCRIBE
import com.example.bottomanimationmydemo.utils.MyConstant.MEAL_SUBSCRIBE_CHECK
import com.example.bottomanimationmydemo.utils.MyConstant.MEAL_SUBSCRIBE_DETAILS
import com.example.bottomanimationmydemo.utils.MyConstant.MEAL_SUBSCRIBE_LIST
import com.example.bottomanimationmydemo.utils.MyConstant.MEAL_SUBSCRIBE_UPDATE
import com.example.bottomanimationmydemo.utils.MyConstant.ORDERCREATE
import com.example.bottomanimationmydemo.utils.MyConstant.SIGNUP
import com.example.bottomanimationmydemo.utils.MyConstant.STARTWORKOUTSTATUS
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

    @POST(MEAL_SUBSCRIBE_LIST)
    suspend fun mealSubscribeList(@Body mealSubscribeListRequest: MealSubscribeListRequest): MealSubscribeListResponse
    @POST(MEAL_SUBSCRIBE_DETAILS)
    suspend fun mealSubscribeDetails(@Body mealSubscriptionDetailsRequest: MealSubscriptionDetailsRequest): JsonObject

    @POST(MEAL_SUBSCRIBE_CHECK)
    suspend fun mealSubscribeCheck(@Body jsonObject: JsonObject): CheckSubscribeModel

    @PUT(MEAL_SUBSCRIBE_UPDATE)
    suspend fun mealSubscribeUpdate(@Body jsonObject: JsonObject): MealPlanSubscriptionUpdateResponse
}