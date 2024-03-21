package com.dev.batchfinal.app_modules.account.repository

import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import okhttp3.RequestBody

class AccountRepository constructor(private val retrofitService: AccountNetworkService){
    fun requestLogin(requestBody: RequestBody) = retrofitService.requestLogin(requestBody)
    fun requestUpdateProfile(headersMap: HashMap<String, String>,requestBody: RequestBody) =
        retrofitService.requestUpdateProfile(headersMap,requestBody)

    fun requestAddDelivaryAddress(headersMap: HashMap<String, String>,requestBody: RequestBody)
    = retrofitService.requestAddDeliveryAddress(headersMap,requestBody)

    fun requestGetDelivaryAddress(headersMap: HashMap<String, String>)
            = retrofitService.requestGetDeliveryAddress(headersMap)
    fun requestSignUp(requestMap: HashMap<String, String>) = retrofitService.requestSignUp(requestMap)

    fun requestProfileImgUpdate(headersMap: HashMap<String, String>,requestBody: RequestBody)=retrofitService.requestProfileImgUpdate(headersMap,requestBody)


}