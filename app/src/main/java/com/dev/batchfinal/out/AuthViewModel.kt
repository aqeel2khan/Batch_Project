package com.dev.batchfinal.out

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.batchfinal.app_modules.batchboard.model.toprated.TopRatedResponse
import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_plan_subscribe.MealSubscribedRequest
import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_plan_subscription_update.MealPlanSubscriptionUpdateResponse
import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_subscription_details_model.MealSubscriptionDetailsRequest
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_arriving.DeliveryArrivingResponse
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_drop_off.DeliveryDropOffResponse
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_time.DeliveryTimeResponse
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.review_list.ReviewModelResponse
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.subscribe.CheckSubscribeModel
import com.dev.batchfinal.app_modules.question.model.all_question.SubmitAllQueResponse
import com.dev.batchfinal.app_modules.question.model.meal_allergies.MealAllergiesResponse
import com.dev.batchfinal.app_modules.question.model.meal_goals.MealGoalsResponse
import com.dev.batchfinal.app_modules.question.model.meal_tags.MealTagsResponse
import com.dev.batchfinal.model.chosen_meal_details_model.ChosenMealDetailsResponse
import com.dev.batchfinal.model.coach_detail_model.CoachDetailResponse
import com.dev.batchfinal.model.coach_filter_list.CoachFilterListResponse
import com.dev.batchfinal.model.coach_list_model.CoachListResponse
import com.dev.batchfinal.model.course_detail.CourseDetailResponse
import com.dev.batchfinal.model.course_filter_model.CourseFilterEntityResponse
import com.dev.batchfinal.model.course_model.CourseListResponse
import com.dev.batchfinal.model.course_workout_list.CourseWorkoutListResponse
import com.dev.batchfinal.model.courseorderlist.CourseOrderList
import com.dev.batchfinal.model.login_model.LoginResponseModel
import com.dev.batchfinal.model.meal_detail_model.MealDetailResponse
import com.dev.batchfinal.model.meal_dish_model.MealDishResponse
import com.dev.batchfinal.model.meal_filter_model.MealFilterResponse
import com.dev.batchfinal.model.meal_list.MealResponseList
import com.dev.batchfinal.model.meal_plan_subscribe.MealsSubscribedRespnse
import com.dev.batchfinal.model.order_model.OrederCreateResponse
import com.dev.batchfinal.model.registeration_model.SignUpResponseModel
import com.dev.batchfinal.model.search_curse_filter.SearchCourseListByFilterResponse
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListRequest
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListResponse

import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
//import net.simplifiedcoding.data.network.Resource

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

    fun filterCourseListApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _courseListResponse.value = Resource.Loading
        _courseListResponse.value = repository.filterCourseList(jsonObject)
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

    fun mealListFilterApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _mealListResponse.value = Resource.Loading
        _mealListResponse.value = repository.getMealListFilter(jsonObject)
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

    // meal Subscribe api
    private val _mealsSubscribedRespnse: MutableLiveData<Resource<MealsSubscribedRespnse>> = MutableLiveData()
    val mealsSubscribedRespnse: LiveData<Resource<MealsSubscribedRespnse>> get() = _mealsSubscribedRespnse

    fun mealSubscribeApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _mealsSubscribedRespnse.value = Resource.Loading
        _mealsSubscribedRespnse.value = repository.mealSubscribe(jsonObject)
    }

    // meal Subscribe List api
    private val _mealSubscribeListResponse: MutableLiveData<Resource<MealSubscribeListResponse>> = MutableLiveData()
    val mealSubscribeListResponse: LiveData<Resource<MealSubscribeListResponse>> get() = _mealSubscribeListResponse

    fun mealSubscribeListApiCall(mealSubscribeListRequest: MealSubscribeListRequest) = viewModelScope.launch {
        _mealSubscribeListResponse.value = Resource.Loading
        _mealSubscribeListResponse.value = repository.mealSubscribeList(mealSubscribeListRequest)
    }

    // meal Subscribe List api
    private val _mealSubscriptionDetailsResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    val mealSubscriptionDetailsResponse: LiveData<Resource<JsonObject>> get() = _mealSubscriptionDetailsResponse

    fun mealSubscribeDetailsApiCall(mealSubscriptionDetailsRequest: MealSubscriptionDetailsRequest) = viewModelScope.launch {
        _mealSubscriptionDetailsResponse.value = Resource.Loading
        _mealSubscriptionDetailsResponse.value = repository.mealSubscribeDetails(mealSubscriptionDetailsRequest)
    }

    // meal Subscribe Check api
    private val _mealCheckSubscribeModelResponse: MutableLiveData<Resource<CheckSubscribeModel>> = MutableLiveData()
    val mealCheckSubscribeModelResponse: LiveData<Resource<CheckSubscribeModel>> get() = _mealCheckSubscribeModelResponse

    fun mealSubscribeCheckApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _mealCheckSubscribeModelResponse.value = Resource.Loading
        _mealCheckSubscribeModelResponse.value = repository.mealSubscribeCheck(jsonObject)
    }

    // meal Subscribe Update api
    private val _mealPlanSubscriptionUpdateResponse: MutableLiveData<Resource<MealPlanSubscriptionUpdateResponse>> = MutableLiveData()
    val mealPlanSubscriptionUpdateResponse: LiveData<Resource<MealPlanSubscriptionUpdateResponse>> get() = _mealPlanSubscriptionUpdateResponse

    fun mealSubscribeUpdateApiCall(userId:String,subscribeId:String,mealId:String,day_dishes:String,day:String,month:String) = viewModelScope.launch {
        _mealPlanSubscriptionUpdateResponse.value = Resource.Loading
        _mealPlanSubscriptionUpdateResponse.value = repository.mealSubscribeUpdate(userId,subscribeId,mealId,day_dishes,day,month)
    }


    // meal Subscribe Check api
    private val _courseSubscribeCheckResponse: MutableLiveData<Resource<CheckSubscribeModel>> = MutableLiveData()
    val courseSubscribeCheckResponse: LiveData<Resource<CheckSubscribeModel>> get() = _courseSubscribeCheckResponse

    fun courseSubscribeCheckApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _courseSubscribeCheckResponse.value = Resource.Loading
        _courseSubscribeCheckResponse.value = repository.courseSubscribeCheck(jsonObject)
    }

    // meal Goals api
    private val _mealGoalsResponse: MutableLiveData<Resource<MealGoalsResponse>> = MutableLiveData()
    val mealGoalsResponse: LiveData<Resource<MealGoalsResponse>> get() = _mealGoalsResponse

    fun mealGoalsApiCall() = viewModelScope.launch {
        _mealGoalsResponse.value = Resource.Loading
        _mealGoalsResponse.value = repository.mealGoalsList()
    }

    // meal Tags api
    private val _mealTagsResponse: MutableLiveData<Resource<MealTagsResponse>> = MutableLiveData()
    val mealTagsResponse: LiveData<Resource<MealTagsResponse>> get() = _mealTagsResponse

    fun mealTagsApiCall() = viewModelScope.launch {
        _mealTagsResponse.value = Resource.Loading
        _mealTagsResponse.value = repository.mealTagsList()
    }

    // meal allergies api
    private val _mealAllergiesResponse: MutableLiveData<Resource<MealAllergiesResponse>> = MutableLiveData()
    val mealAllergiesResponse: LiveData<Resource<MealAllergiesResponse>> get() = _mealAllergiesResponse

    fun mealAllergiesApiCall() = viewModelScope.launch {
        _mealAllergiesResponse.value = Resource.Loading
        _mealAllergiesResponse.value = repository.mealAllergies()
    }

    // meal all submit question api
    private val _submitAllQuestionResponse: MutableLiveData<Resource<SubmitAllQueResponse>> = MutableLiveData()
    val submitAllQuestionResponse: LiveData<Resource<SubmitAllQueResponse>> get() = _submitAllQuestionResponse

    fun submitAllQuestionApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        _submitAllQuestionResponse.value = Resource.Loading
        _submitAllQuestionResponse.value = repository.submitQueApi(jsonObject)
    }

    // top Rated api
    private val _topRatedResponseResponse: MutableLiveData<Resource<MealResponseList>> = MutableLiveData()
    val topRatedResponseResponse: LiveData<Resource<MealResponseList>> get() = _topRatedResponseResponse

    fun topRatedApiCall() = viewModelScope.launch {
        _topRatedResponseResponse.value = Resource.Loading
        _topRatedResponseResponse.value = repository.topRatedApi()
    }

    // Review list api
    private val _reviewModelResponse: MutableLiveData<Resource<ReviewModelResponse>> = MutableLiveData()
    val reviewModelResponse: LiveData<Resource<ReviewModelResponse>> get() = _reviewModelResponse

    fun mealDishReviewApiCall(id:Int) = viewModelScope.launch {
        _reviewModelResponse.value = Resource.Loading
        _reviewModelResponse.value = repository.mealDishReviewApi(id)
    }

    // Delivery Time api
    private val _deliveryTimeResponse: MutableLiveData<Resource<DeliveryTimeResponse>> = MutableLiveData()
    val deliveryTimeResponse: LiveData<Resource<DeliveryTimeResponse>> get() = _deliveryTimeResponse

    fun deliveryTimeApiCall() = viewModelScope.launch {
        _deliveryTimeResponse.value = Resource.Loading
        _deliveryTimeResponse.value = repository.deliveryTimeApi()
    }

    // Delivery Arriving api
    private val _deliveryArrivingResponse: MutableLiveData<Resource<DeliveryArrivingResponse>> = MutableLiveData()
    val deliveryArrivingResponse: LiveData<Resource<DeliveryArrivingResponse>> get() = _deliveryArrivingResponse

    fun deliveryArrivingApiCall() = viewModelScope.launch {
        _deliveryArrivingResponse.value = Resource.Loading
        _deliveryArrivingResponse.value = repository.deliveryArrivingApi()
    }

    // Delivery Arriving api
    private val _deliveryDropOffResponse: MutableLiveData<Resource<DeliveryArrivingResponse>> = MutableLiveData()
    val deliveryDropOffResponse: LiveData<Resource<DeliveryArrivingResponse>> get() = _deliveryDropOffResponse

    fun deliveryDropApiCall() = viewModelScope.launch {
        _deliveryDropOffResponse.value = Resource.Loading
        _deliveryDropOffResponse.value = repository.deliveryDropApi()
    }
}