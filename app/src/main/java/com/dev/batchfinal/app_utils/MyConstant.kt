package com.dev.batchfinal.app_utils

import com.google.gson.JsonObject

object MyConstant {
    const val BASE_URL = "http://admin.batch.com.co/public/api/v1/"
    const val IMAGE_BASE_URL = "http://admin.batch.com.co/public/"
    const val DEVICE_TYPE= "1"
    const val CAMERA_REQUEST_CODE = 103
    const val GALLERY_REQUEST_CODE = 104
    var isAdded = false
    var isRefresh = false

    const val AUTHORIZATION ="Authorization"
    var key ="KEY"
    val jsonObject = JsonObject()
    const val  success = true
    const val  status = true

    const val newPassword ="NEW_PASSWORD"
    const val password ="PASSWORD"
    const val rePassword ="RE_PASSWORD"
    const val oldPassword ="OLD_PASSWORD"
    const val confirmNewPassword ="CONFIRM_NEW_PASSWORD"

    //auth
    const val LOGIN = "auth/signin"
    const val SIGNUP = "auth/signup"
    //training
    const val COACH = "coach/list"
    const val COACHDETAIL = "coach/detail/{id}"
    const val COURSE = "course/list"
    const val COURSEDETAIL = "course/detail/{id}"
    const val COURSEWORKOUTLIST = "course/workout/list/{id}"
    const val COURSEFILTERENTITY = "course/filter/entity"
    const val COURSEPROMOCODELIST = "course/promocode/list"
    const val COACHFILTERENTITY = "coach/filter/entity"
    const val ORDERCREATE = "course/order/create"
    const val COURSEORDERLIST = "course/order/list"
    const val STARTWORKOUTSTATUS = "/course/order/workout/status"

    //meal
    const val MEALLIST = "meal/list"
    const val MEALDETAILS = "meal/details/{id}"
    const val MEALFILTER = "meal/filters"
    const val MEALDISH = "meal/dishes/{id}"
    const val CHOSEN_MEAL_DETAILS = "dish/details"
    const val MEAL_SUBSCRIBE = "subscription/subscribe"
    const val MEAL_SUBSCRIBE_LIST = "subscription/list"
    const val MEAL_SUBSCRIBE_DETAILS = "subscription/details"

//    const val DOLLER_SIGN = "$"
}