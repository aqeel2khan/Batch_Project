package com.dev.batchfinal.app_modules.notifications.model

import com.google.gson.annotations.SerializedName

data class NotificationsModel(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : NotificationsData?    = NotificationsData(),
    @SerializedName("error"   ) var error   : Error?   = Error()
)
data class NotificationsData
    (
    @SerializedName("id"          ) var id         : Int?    = null,
    @SerializedName("user_id"     ) var userId     : Int?    = null,
    @SerializedName("all"         ) var all        : Int?    = null,
    @SerializedName("training"    ) var training   : Int?    = null,
    @SerializedName("live_stream" ) var liveStream : Int?    = null,
    @SerializedName("meal_plan"   ) var mealPlan   : Int?    = null,
    @SerializedName("delivery"    ) var delivery   : Int?    = null,
    @SerializedName("created_at"  ) var createdAt  : String? = null,
    @SerializedName("updated_at"  ) var updatedAt  : String? = null
            )