package com.dev.batchfinal.model.course_promocode


import com.google.gson.annotations.SerializedName

data class CousePromocodeResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Error,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<PromoCodeList>
)

data class PromoCodeList (
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("discount_type")
    val discountType: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("promo_code")
    val promoCode: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

class Error