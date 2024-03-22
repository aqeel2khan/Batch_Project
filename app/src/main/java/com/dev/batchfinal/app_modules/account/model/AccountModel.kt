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

/**
 *
 *  ADD DELIVARY ADDRESS MODEL
 * */

data class DeliveryAddressModel
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null
            )

// GET DELIVARY ADDRESS

data class GetDelivaryAddressModel
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : GetDelivaryAddresData?    = GetDelivaryAddresData(),
    @SerializedName("error"   ) var error   : Error?   = Error()
            )
data class GetDelivaryAddresData
    (
    @SerializedName("id"             ) var id           : Int?    = null,
    @SerializedName("user_id"        ) var userId       : Int?    = null,
    @SerializedName("address_line_1" ) var addressLine1 : String? = null,
    @SerializedName("address_line_2" ) var addressLine2 : String? = null,
    @SerializedName("city"           ) var city         : String? = null,
    @SerializedName("postal_code"    ) var postalCode   : String? = null,
    @SerializedName("state"          ) var state        : String? = null,
    @SerializedName("country"        ) var country      : String? = null,
    @SerializedName("type"           ) var type         : String? = null,
    @SerializedName("is_default"     ) var isDefault    : Int?    = null,
    @SerializedName("created_at"     ) var createdAt    : String? = null,
    @SerializedName("updated_at"     ) var updatedAt    : String? = null

)

//SIGN UP

data class SignUpModel
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : SignUpData?    = SignUpData(),
    @SerializedName("token"   ) var token   : String?  = null


)
data class SignUpData
    (
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("mobile"     ) var mobile    : String? = null,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("dob"        ) var dob       : String? = null,
    @SerializedName("gender"     ) var gender    : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null

)




data class SignUpError
    (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("errors"  ) var errors  : Errors?  = Errors()
            )
data class Errors (

    @SerializedName("email" ) var email : ArrayList<String> = arrayListOf()

)


//PROFILE IMAGE

data class UpdateProfileImg
    (
    @SerializedName("status") var status: Boolean?=null,
    @SerializedName("message") val message: String?=null,
    @SerializedName("data")
    val data: UpdateProfileImgData
            )

data class UpdateProfileImgData
    (
    @SerializedName("id") val id: Int?=null,
    @SerializedName("name") val name: String?=null,
    @SerializedName("mobile") val mobile: String?=null,
    @SerializedName("email") val email: String?=null,
    @SerializedName("dob") val dob: String?=null,
    @SerializedName("gender") val gender: String?=null,
    @SerializedName("profile_photo_path") val profilePhotoPath: String?=null,
    @SerializedName("created_at") val createdAt: String?=null,
    @SerializedName("updated_at") val updatedAt: String?=null

            )

