package com.example.bottomanimationmydemo.model.registeration_model


import com.google.gson.annotations.SerializedName

data class SignUpResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String
)

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("dob")
    val dob: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)