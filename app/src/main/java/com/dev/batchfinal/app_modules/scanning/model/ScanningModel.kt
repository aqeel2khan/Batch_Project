package com.dev.batchfinal.app_modules.scanning.model

import com.dev.batchfinal.model.courseorderlist.Course_detail
import com.google.gson.annotations.SerializedName

//COURSE LIST MODEL

data class CourseOrderListModel(
    @SerializedName("data") val `data`: Data,
    @SerializedName("error") val error: Error,
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
    @SerializedName("subtotal") val subtotal: String,
    @SerializedName("discount") val discount: String,
    @SerializedName("tax") val tax: String,
    @SerializedName("total") val total: String,
    @SerializedName("payment_type") val payment_type: String,
    @SerializedName("transaction_id") val transaction_id: String,
    @SerializedName("payment_status") val payment_status: String,
    @SerializedName("status") val status: String,
    @SerializedName("course_detail") val course_detail: Course_detail
)


/**
 *
 * SUBSCRIPTION LIST MODEL
 *
 * */

data class MealSubscriptionListModel
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : SubscriptionListData?    = SubscriptionListData(),
    @SerializedName("error"   ) var error   : Error?   = Error()

            )
data class SubscriptionListData(
    @SerializedName("data"         ) var data         : ArrayList<String> = arrayListOf(),
    @SerializedName("status"       ) var status       : String?           = null,
    @SerializedName("recordsTotal" ) var recordsTotal : Int?              = null
)

