package com.dev.batchfinal.app_modules.notifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.dev.batchfinal.app_modules.notifications.repository.NotificationRepository

class NotificationFactoryModel constructor(private val repository: NotificationRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return  if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {

            NotificationViewModel(this.repository) as T

        }  else
        {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
