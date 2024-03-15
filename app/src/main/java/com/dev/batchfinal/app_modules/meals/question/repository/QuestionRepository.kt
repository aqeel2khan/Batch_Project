package com.dev.batchfinal.app_modules.question.repository

import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import com.dev.batchfinal.app_modules.question.network_service.QuestionNetworkService
import okhttp3.RequestBody

class QuestionRepository constructor(private val retrofitService: QuestionNetworkService){
    fun requestMealGoals(requestBody: RequestBody) = retrofitService.requestMealGoals(requestBody)

}