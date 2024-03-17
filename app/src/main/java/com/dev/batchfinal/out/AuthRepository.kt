package com.dev.batchfinal.out

import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_plan_subscribe.MealSubscribedRequest
import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_subscription_details_model.MealSubscriptionDetailsRequest
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListRequest
import com.dev.batchfinal.network.ApiService

import com.google.gson.JsonObject
import net.simplifiedcoding.data.network.SafeApiCall
import java.util.HashMap

import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: ApiService) : SafeApiCall {

    suspend fun loginApi(jsonObject: JsonObject)= safeApiCall {
        api.loginApi(jsonObject)
    }

    suspend fun signUp(jsonObject: JsonObject) = safeApiCall {
        api.signUpApi(jsonObject)
    }

    suspend fun coachList() = safeApiCall {
        api.coachList()
    }
    suspend fun searchCoachList(jsonObject: JsonObject) = safeApiCall {
        api.searchCoachList(jsonObject)
    }

    suspend fun coachDetail(user_type: Int) = safeApiCall {
        api.coachDetail(user_type)
    }

    suspend fun courseList() = safeApiCall {
        api.courseList()
    }
    suspend fun filterCourseList(jsonObject: JsonObject) = safeApiCall {
        api.filterCourseList(jsonObject)
    }

    suspend fun courseDetail(course_id: String) = safeApiCall {
        api.courseDetail(course_id)
    }

    suspend fun courseWorkoutList(course_id: String) = safeApiCall {
        api.courseWorkoutList(course_id)
    }

    suspend fun coachCourseList(jsonObject: JsonObject) = safeApiCall {
        api.coachCourseList(jsonObject)
    }

    suspend fun courseFilterEntity() = safeApiCall {
        api.courseFilterEntity()
    }

    suspend fun coursePromoCodeList() = safeApiCall {
        api.coursePromoCode()
    }

    suspend fun coachFilterEntity() = safeApiCall {
        api.coachFilterEntity()
    }

    suspend fun courseOrderCreate(token: String, jsonObject: JsonObject) = safeApiCall {
        api.courseOrderCreate(token, jsonObject)
    }

    suspend fun courseOrderList(token: String) = safeApiCall {
        api.courseOrderList(token)
    }

    suspend fun courseStart(token: String, jsonObject: JsonObject) = safeApiCall {
        api.startWorkout(token, jsonObject)
    }

    suspend fun searchCourseListByFilterApi(jsonObject: JsonObject) = safeApiCall {
        api.searchCourseListByFilter(jsonObject)
    }

    suspend fun getMealList() = safeApiCall {
        api.getMealList()
    }
    suspend fun getMealListFilter(jsonObject: JsonObject) = safeApiCall {
        api.getMealListFilter(jsonObject)
    }

    suspend fun mealDetail(meal_id: String) = safeApiCall {
        api.mealDetails(meal_id)
    }

    suspend fun mealFilter() = safeApiCall {
        api.mealFilter()
    }

    suspend fun mealDish(id: Int) = safeApiCall {
        api.mealDishData(id)
    }

    suspend fun getDishDetails(jsonObject: JsonObject) = safeApiCall {
        api.getDishDetails(jsonObject)
    }

    suspend fun mealSubscribe(mealSubscribedRequest: MealSubscribedRequest) = safeApiCall {
        api.mealSubscribe(mealSubscribedRequest)
    }

    suspend fun mealSubscribeList(mealSubscribeListRequest: MealSubscribeListRequest) = safeApiCall {
        api.mealSubscribeList(mealSubscribeListRequest)
    }

    suspend fun mealSubscribeDetails(mealSubscriptionDetailsRequest: MealSubscriptionDetailsRequest) = safeApiCall {
        api.mealSubscribeDetails(mealSubscriptionDetailsRequest)
    }

    suspend fun mealSubscribeCheck(jsonObject: JsonObject) = safeApiCall {
        api.mealSubscribeCheck(jsonObject)
    }
    suspend fun courseSubscribeCheck(jsonObject: JsonObject) = safeApiCall {
        api.courseSubscribeCheck(jsonObject)
    }

    suspend fun mealSubscribeUpdate(userId:String,subscribeId:String,mealId:String,day_dishes:String,day:String,month:String) = safeApiCall {
        api.mealSubscribeUpdate(userId,subscribeId,mealId,day_dishes,day,month)
    }

    suspend fun mealGoalsList() = safeApiCall {
        api.mealGoalsList()
    }

    suspend fun mealTagsList() = safeApiCall {
        api.mealTagsList()
    }
    suspend fun mealAllergies() = safeApiCall {
        api.mealAllergiesList()
    }

    suspend fun submitQueApi(jsonObject: JsonObject) = safeApiCall {
        api.submitAllQuestion(jsonObject)
    }
    suspend fun topRatedApi() = safeApiCall {
        api.topRated()
    }

    suspend fun mealDishReviewApi(id:Int) = safeApiCall {
        api.mealDishReview(id)
    }

    suspend fun deliveryTimeApi() = safeApiCall {
        api.deliveryTime()
    }

    suspend fun deliveryArrivingApi() = safeApiCall {
        api.deliveryArriving()
    }
    suspend fun deliveryDropApi() = safeApiCall {
        api.deliveryDrop()
    }

}