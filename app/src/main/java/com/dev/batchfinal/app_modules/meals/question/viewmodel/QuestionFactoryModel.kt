package com.dev.batchfinal.app_modules.question.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_modules.question.repository.QuestionRepository

class QuestionFactoryModel constructor(private val repository: QuestionRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return  if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {

            QuestionViewModel(this.repository) as T

        }  else
        {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
