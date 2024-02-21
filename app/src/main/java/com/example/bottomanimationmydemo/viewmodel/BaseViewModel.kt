package com.example.bottomanimationmydemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomanimationmydemo.factory.IFactory
import com.example.bottomanimationmydemo.model.RequestType
import com.example.bottomanimationmydemo.network.errorhandling.ErrorHandler
import dagger.Provides

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

open class BaseViewModel(): ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner


    lateinit var errorHandlerFactory: IFactory<RequestType, ErrorHandler>

    fun executeRoutine(requestType: RequestType = RequestType.POST_LOGIN, execute: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _spinner.postValue(true)
                try {
                    execute()
                } catch (ex: HttpException) {
                    val errorHandler = errorHandlerFactory.create(requestType)
                    _error.postValue(errorHandler.getErrorMessageFrom(ex))
                }catch (ex: SocketTimeoutException) {
                    val errorHandler = errorHandlerFactory.create(requestType)
                    _error.postValue(errorHandler.getErrorMessageFrom(ex))
                } catch (ex: Exception) {
                    _error.postValue(ex.localizedMessage)
                    Timber.e(ex)
                } finally {
                    _spinner.postValue(false)
                }
            }
        }
    }

    fun executeRoutineWithoutLoader(requestType: RequestType = RequestType.POST_LOGIN, execute: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                _spinner.postValue(true)
                try {
                    execute()
                } catch (ex: HttpException) {
                    val errorHandler = errorHandlerFactory.create(requestType)
                    _error.postValue(errorHandler.getErrorMessageFrom(ex))
                }catch (ex: SocketTimeoutException) {
                    val errorHandler = errorHandlerFactory.create(requestType)
                    _error.postValue(errorHandler.getErrorMessageFrom(ex))
                } catch (ex: Exception) {
                    _error.postValue(ex.localizedMessage)
                    Timber.e(ex)
                } finally {
//                    _spinner.postValue(false)
                }
            }
        }
    }
}