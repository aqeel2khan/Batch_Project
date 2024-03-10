package com.dev.batchfinal.app_modules.scanning.model

import com.google.gson.annotations.SerializedName

class MealSubscriptionDetailsResponse {
    @SerializedName("data")
    var data: Data? = null

    @SerializedName("error")
    var error: Error? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("status")
    var status: Boolean? = null

    inner class Data {
        @SerializedName("data")
        var internalData: InternalData? = null

        @SerializedName("status")
        var status: String? = null
    }

    inner class InternalData {
        @SerializedName("meal_details")
        var mealDetails: MealDetails? = null

        @SerializedName("subscribe_detail")
        var subscribeDetail: SubscribeDetail? = null
    }

    inner class MealDetails {
        @SerializedName("avg_cal_per_day")
        var avgCalPerDay: String? = null

        @SerializedName("category_list")
        var categoryList: List<CategoryList>? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("description_ar")
        var descriptionAr: String? = null

        @SerializedName("discount")
        var discount: Long? = null

        @SerializedName("duration")
        var duration: String? = null

        @SerializedName("meal_count")
        var mealCount: Long? = null

        @SerializedName("meal_type")
        var mealType: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("name_ar")
        var nameAr: String? = null

        @SerializedName("price")
        var price: String? = null

        @SerializedName("snack_count")
        var snackCount: Long? = null
    }

    inner class SubscribeDetail {
        @SerializedName("days_dishes")
        var daysDishes: DaysDishes? = null

        @SerializedName("end_date")
        var endDate: String? = null

        @SerializedName("selected_duration")
        var selectedDuration: Long? = null

        @SerializedName("start_date")
        var startDate: String? = null

        @SerializedName("subscribed_id")
        var subscribedId: String? = null
    }

    inner class CategoryList {
        @SerializedName("category_id")
        var categoryId: Long? = null

        @SerializedName("category_name")
        var categoryName: String? = null

        @SerializedName("category_type")
        var categoryType: String? = null
    }

    inner class DaysDishes
    inner class Error
}
