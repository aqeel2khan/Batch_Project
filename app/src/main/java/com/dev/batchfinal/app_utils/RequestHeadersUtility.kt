package com.dev.batchfinal.app_utils

class RequestHeadersUtility {

    companion object
    {
        fun requestHeaderAuthorization(authToken:String): HashMap<String, String> {
            val authHeader = HashMap<String, String>()
            authHeader["Authorization"] = "Bearer $authToken"
            return authHeader
        }
    }
}