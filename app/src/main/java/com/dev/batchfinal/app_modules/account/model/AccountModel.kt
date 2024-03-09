package com.dev.batchfinal.app_modules.account.model

import com.google.gson.annotations.SerializedName


/***
 *
 *LOGIN -MODEL
 * @Created@BBh
 * */
data class SignInModel
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : SignInData?    = SignInData(),
    @SerializedName("token"   ) var token   : String?  = null

            )
data class SignInData
    (
    @SerializedName("id"                 ) var id               : Int?    = null,
    @SerializedName("name"               ) var name             : String? = null,
    @SerializedName("mobile"             ) var mobile           : String? = null,
    @SerializedName("email"              ) var email            : String? = null,
    @SerializedName("dob"                ) var dob              : String? = null,
    @SerializedName("gender"             ) var gender           : String? = null,
    @SerializedName("profile_photo_path" ) var profilePhotoPath : String? = null,
    @SerializedName("created_at"         ) var createdAt        : String? = null,
    @SerializedName("updated_at"         ) var updatedAt        : String? = null
            )

/**
 * UPDATE PROFILE -MODEL
 * @Created @BBh
 *
 * */
data class UpdateProfileModel
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : UpdateProfileData?    = UpdateProfileData()
            )

data class UpdateProfileData
    (
    @SerializedName("id"                 ) var id               : Int?    = null,
    @SerializedName("name"               ) var name             : String? = null,
    @SerializedName("mobile"             ) var mobile           : String? = null,
    @SerializedName("email"              ) var email            : String? = null,
    @SerializedName("dob"                ) var dob              : String? = null,
    @SerializedName("gender"             ) var gender           : String? = null,
    @SerializedName("profile_photo_path" ) var profilePhotoPath : String? = null,
    @SerializedName("created_at"         ) var createdAt        : String? = null,
    @SerializedName("updated_at"         ) var updatedAt        : String? = null
            )