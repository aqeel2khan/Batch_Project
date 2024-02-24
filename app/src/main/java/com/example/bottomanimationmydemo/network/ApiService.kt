package com.example.bottomanimationmydemo.network

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
import com.example.bottomanimationmydemo.model.order_model.OrederCreateResponse
import com.example.bottomanimationmydemo.model.registeration_model.SignUpResponseModel
import com.example.bottomanimationmydemo.model.search_curse_filter.SearchCourseListByFilterResponse
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyConstant.AUTHORIZATION
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
}