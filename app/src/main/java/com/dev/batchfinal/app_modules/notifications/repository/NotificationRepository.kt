package com.dev.batchfinal.app_modules.notifications.repository

import com.dev.batchfinal.app_modules.notifications.network_service.NotificationNetworkService

class NotificationRepository constructor(private val retrofitService: NotificationNetworkService) {
    fun requestNotification(headersMap: HashMap<String, String>) = retrofitService.requestNotification(headersMap)

}