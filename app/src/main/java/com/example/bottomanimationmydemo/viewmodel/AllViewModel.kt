package com.example.bottomanimationmydemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bottomanimationmydemo.model.login_model.LoginResponseModel
import com.example.bottomanimationmydemo.repository.UserRepository
import com.example.bottomanimationmydemo.utils.NetworkErrorResult
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val userRepo: UserRepository
): BaseViewModel() {

    // Login Api
    var _loginResponse: MutableLiveData<NetworkErrorResult<LoginResponseModel>> = MutableLiveData()
    var loginResponse: LiveData<NetworkErrorResult<LoginResponseModel>> = _loginResponse
        get() = _loginResponse

    fun loginApiCall(jsonObject: JsonObject) = viewModelScope.launch {
        userRepo.loginApi(jsonObject).collect { values ->
            _loginResponse.value = values

        }
    }

}