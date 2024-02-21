package com.example.bottomanimationmydemo.model.coach_detail_model


import com.google.gson.annotations.SerializedName

data class CoachDetailResponse(
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
    @SerializedName("avatar")
    val avatar: Any,
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("currnecy")
    val currnecy: String,
    @SerializedName("device_token")
    val deviceToken: Any,
    @SerializedName("dob")
    val dob: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("fname")
    val fname: String,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_login_at")
    val lastLoginAt: Any,
    @SerializedName("last_login_ip")
    val lastLoginIp: Any,
    @SerializedName("lname")
    val lname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("profile_photo_path")
    val profilePhotoPath: String,
    @SerializedName("updated_at")
    val updatedAt: Any,
    @SerializedName("user_status")
    val userStatus: Any,
    @SerializedName("user_type")
    val userType: Int,
    @SerializedName("verification_code")
    val verificationCode: Any,
    @SerializedName("website")
    val website: String
)

class Error