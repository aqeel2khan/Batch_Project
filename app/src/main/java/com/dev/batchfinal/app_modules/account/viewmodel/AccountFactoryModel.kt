package com.dev.batchfinal.app_modules.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.batchfinal.app_modules.account.repository.AccountRepository

class AccountFactoryModel constructor(private val repository: AccountRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return  if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {

            AccountViewModel(this.repository) as T

        }  else
        {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
