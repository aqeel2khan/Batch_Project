package com.example.bottomanimationmydemo.out

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomanimationmydemo.model.chosen_meal_details_model.ChosenMealDetailsResponse
import com.example.bottomanimationmydemo.model.coach_detail_model.CoachDetailResponse
import com.example.bottomanimationmydemo.model.coach_filter_list.CoachFilterListResponse
import com.example.bottomanimationmydemo.model.coach_list_model.CoachListResponse
import com.example.bottomanimationmydemo.model.course_detail.CourseDetailResponse
import com.example.bottomanimationmydemo.model.course_filter_model.CourseFilterEntityResponse
import com.example.bottomanimationmydemo.model.course_model.CourseListResponse
import com.example.bottomanimationmydemo.model.course_workout_list.CourseWorkoutListResponse
import com.example.bottomanimationmydemo.model.courseorderlist.CourseOrderList
import com.example.bottomanimationmydemo.model.login_model.LoginResponseModel
import com.example.bottomanimationmydemo.model.meal_detail_model.MealDetailResponse
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishResponse
import com.example.bottomanimationmydemo.model.meal_filter_model.MealFilterResponse
import com.example.bottomanimationmydemo.model.meal_list.MealResponseList
import com.example.bottomanimationmydemo.model.meal_plan_subscribe.MealSubscribedRequest
import com.example.bottomanimationmydemo.model.meal_plan_subscribe.MealsSubscribedRespnse
import com.example.bottomanimationmydemo.model.order_model.OrederCreateResponse
import com.example.bottomanimationmydemo.model.registeration_model.SignUpResponseModel
import com.example.bottomanimationmydemo.model.search_curse_filter.SearchCourseListByFilterResponse
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    // Login Api
    private val _loginResponse: MutableLiveData<Resource<LoginResponseModel>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponseModel>> get() = _loginResponse

    fun loginResponseApi(jsonObject: JsonObject) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.loginApi(jsonObject)
    }

    // Sign up Api
    private val _signUpResponse: MutableLiveData<Resource<SignUpResponseModel>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<SignUpResponseModel>> get() = _signUpResponse

    fun signUpApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _signUpResponse.value = Resource.Loading
        _signUpResponse.value = repository.signUp(jsonObject)
    }

    // Coach List Api
    private val _coachListResponse: MutableLiveData<Resource<CoachListResponse>> = MutableLiveData()
    val coachListResponse: LiveData<Resource<CoachListResponse>> get() = _coachListResponse

    fun coachListApiCall() = viewModelScope.launch {
        _coachListResponse.value = Resource.Loading
        _coachListResponse.value = repository.coachList()
    }
    fun searchCoachListApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _coachListResponse.value = Resource.Loading
        _coachListResponse.value = repository.searchCoachList(jsonObject)
    }

    // coach detail api
    private val _coachDetailResponse: MutableLiveData<Resource<CoachDetailResponse>> = MutableLiveData()
    val coachDetailResponse: LiveData<Resource<CoachDetailResponse>> get() = _coachDetailResponse

    fun coachDetailApiCall(user_type: Int) = viewModelScope.launch {
        _coachDetailResponse.value = Resource.Loading
        _coachDetailResponse.value = repository.coachDetail(user_type)
    }

    // Course List Api
    private val _courseListResponse: MutableLiveData<Resource<CourseListResponse>> = MutableLiveData()
    val courseListResponse: LiveData<Resource<CourseListResponse>> get() = _courseListResponse

    fun courseListApiCall() = viewModelScope.launch {
        _courseListResponse.value = Resource.Loading
        _courseListResponse.value = repository.courseList()
    }

    // course detail api
    private val _courseDetailResponse: MutableLiveData<Resource<CourseDetailResponse>> = MutableLiveData()
    val courseDetailResponse: LiveData<Resource<CourseDetailResponse>> get() = _courseDetailResponse

    fun courseDetailApiCall(user_type: String) = viewModelScope.launch {
        _courseDetailResponse.value = Resource.Loading
        _courseDetailResponse.value = repository.courseDetail(user_type)
    }

    // course workout list api
    private val _courseWorkoutListResponse: MutableLiveData<Resource<CourseWorkoutListResponse>> = MutableLiveData()
    val courseWorkoutListResponse: LiveData<Resource<CourseWorkoutListResponse>> get() = _courseWorkoutListResponse

    fun courseWorkoutListApiCall(user_type: String) = viewModelScope.launch {
        _courseWorkoutListResponse.value = Resource.Loading
        _courseWorkoutListResponse.value = repository.courseWorkoutList(user_type)
    }

    private val _coachCourseListResponse: MutableLiveData<Resource<CourseListResponse>> = MutableLiveData()
    val coachCourseListResponse: LiveData<Resource<CourseListResponse>> get() = _coachCourseListResponse

    fun coachCourseListApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _coachCourseListResponse.value = Resource.Loading
        _coachCourseListResponse.value = repository.coachCourseList(jsonObject)
    }

    // course filter entity api
    private val _courseFilterEntityResponse: MutableLiveData<Resource<CourseFilterEntityResponse>> = MutableLiveData()
    val courseFilterEntityResponse: LiveData<Resource<CourseFilterEntityResponse>> get() = _courseFilterEntityResponse

    fun courseFilterEntityApiCall() = viewModelScope.launch {
        _courseFilterEntityResponse.value = Resource.Loading
        _courseFilterEntityResponse.value = repository.courseFilterEntity()
    }

    // course promo code list api
    private val _coursePromoCodeListResponse: MutableLiveData<Resource<CourseFilterEntityResponse>> = MutableLiveData()
    val coursePromoCodeListResponse: LiveData<Resource<CourseFilterEntityResponse>> get() = _coursePromoCodeListResponse

    fun coursePromoCodeListApiCall() = viewModelScope.launch {
        _coursePromoCodeListResponse.value = Resource.Loading
        _coursePromoCodeListResponse.value = repository.courseFilterEntity()
    }

    // coach filter entity api
    private val _coachFilterEntityResponse: MutableLiveData<Resource<CoachFilterListResponse>> = MutableLiveData()
    val coachFilterEntityResponse: LiveData<Resource<CoachFilterListResponse>> get() = _coachFilterEntityResponse

    fun coachFilterEntityApiCall() = viewModelScope.launch {
        _coachFilterEntityResponse.value = Resource.Loading
        _coachFilterEntityResponse.value = repository.coachFilterEntity()
    }

    // course order create api
    private val _courseOrderCreateResponse: MutableLiveData<Resource<OrederCreateResponse>> = MutableLiveData()
    val courseOrderCreateResponse: LiveData<Resource<OrederCreateResponse>> get() = _courseOrderCreateResponse

    fun courseOrderCreateApiCall(token: String, jsonObject: JsonObject) = viewModelScope.launch {
        _courseOrderCreateResponse.value = Resource.Loading
        _courseOrderCreateResponse.value = repository.courseOrderCreate(token, jsonObject)
    }

    //course order List
    private val _courseOrderListResponse: MutableLiveData<Resource<CourseOrderList>> = MutableLiveData()
    val courseOrderListResponse: LiveData<Resource<CourseOrderList>> get() = _courseOrderListResponse

    fun courseOrderListApiCall(token: String) = viewModelScope.launch {
        _courseOrderListResponse.value = Resource.Loading
        _courseOrderListResponse.value = repository.courseOrderList(token)
    }
        //course start api
    private val _courseStartResponse: MutableLiveData<Resource<OrederCreateResponse>> = MutableLiveData()
    val courseStartResponse: LiveData<Resource<OrederCreateResponse>> get() = _courseOrderCreateResponse

    fun courseStartApiCall(token: String, jsonObject: JsonObject) = viewModelScope.launch {
        _courseStartResponse.value = Resource.Loading
        _courseStartResponse.value = repository.courseStart(token, jsonObject)
    }


    //search course List filter
    private val _searchCourseFilterListResponse: MutableLiveData<Resource<SearchCourseListByFilterResponse>> = MutableLiveData()
    val searchCourseFilterListResponse: LiveData<Resource<SearchCourseListByFilterResponse>> get() = _searchCourseFilterListResponse

    fun searchCourseFilterListApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _searchCourseFilterListResponse.value = Resource.Loading
        _searchCourseFilterListResponse.value = repository.searchCourseListByFilterApi(jsonObject)
    }

    //meal List filter
    private val _mealListResponse: MutableLiveData<Resource<MealResponseList>> = MutableLiveData()
    val mealListResponse: LiveData<Resource<MealResponseList>> get() = _mealListResponse

    fun mealListApiCall() = viewModelScope.launch {
        _mealListResponse.value = Resource.Loading
        _mealListResponse.value = repository.getMealList()
    }

    // meal detail api
    private val _mealDetailResponse: MutableLiveData<Resource<MealDetailResponse>> = MutableLiveData()
    val mealDetailResponse: LiveData<Resource<MealDetailResponse>> get() = _mealDetailResponse

    fun mealDetailApiCall(meal_id: String) = viewModelScope.launch {
        _mealDetailResponse.value = Resource.Loading
        _mealDetailResponse.value = repository.mealDetail(meal_id)
    }

    // meal detail api
    private val _mealFilterResponse: MutableLiveData<Resource<MealFilterResponse>> = MutableLiveData()
    val mealFilterResponse: LiveData<Resource<MealFilterResponse>> get() = _mealFilterResponse

    fun mealFilterApiCall() = viewModelScope.launch {
        _mealFilterResponse.value = Resource.Loading
        _mealFilterResponse.value = repository.mealFilter()
    }

    // meal detail api
    private val _mealDishResponse: MutableLiveData<Resource<MealDishResponse>> = MutableLiveData()
    val mealDishResponse: LiveData<Resource<MealDishResponse>> get() = _mealDishResponse

    fun mealDishApiCall(cat_id: Int) = viewModelScope.launch {
        _mealDishResponse.value = Resource.Loading
        _mealDishResponse.value = repository.mealDish(cat_id)
    }

    // dish details api
    private val _dishDetailsResponse: MutableLiveData<Resource<ChosenMealDetailsResponse>> = MutableLiveData()
    val dishDetailsResponse: LiveData<Resource<ChosenMealDetailsResponse>> get() = _dishDetailsResponse

    fun getDishDetailsApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _dishDetailsResponse.value = Resource.Loading
        _dishDetailsResponse.value = repository.getDishDetails(jsonObject)
    }

    // dish details api
    private val _mealsSubscribedRespnse: MutableLiveData<Resource<MealsSubscribedRespnse>> = MutableLiveData()
    val mealsSubscribedRespnse: LiveData<Resource<MealsSubscribedRespnse>> get() = _mealsSubscribedRespnse

    fun mealSubscribeApiCall(mealSubscribedRequest: MealSubscribedRequest) = viewModelScope.launch {
        _mealsSubscribedRespnse.value = Resource.Loading
        _mealsSubscribedRespnse.value = repository.mealSubscribe(mealSubscribedRequest)
    }
}