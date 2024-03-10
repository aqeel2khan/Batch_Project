package com.dev.batchfinal.app_modules.scanning.repository

import com.dev.batchfinal.app_modules.scanning.network_service.ScanningNetworkService
import okhttp3.RequestBody

class ScanningRepository constructor(private val retrofitService: ScanningNetworkService) {

    fun requestMealSubsCription(headersMap: HashMap<String, String>,requestBody: RequestBody) =
        retrofitService.requestMealSubsCription(headersMap,requestBody)

    fun requestCourseOrder(headersMap: HashMap<String, String>,requestBody: RequestBody) =
        retrofitService.requestCourseOrder(headersMap,requestBody)


}