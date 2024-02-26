package com.example.bottomanimationmydemo.out

import com.example.bottomanimationmydemo.network.ApiService
import com.google.gson.JsonObject
import net.simplifiedcoding.data.network.SafeApiCall

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

 /*   suspend fun getMealList() = safeApiCall {
        api.getMealList()
    }*/
}