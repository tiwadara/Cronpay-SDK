package com.cronpay.cronpaysdkexample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cronpay.cronpaysdkexample.constant.Constants.ERROR_MESSAGE
import com.cronpay.cronpaysdkexample.constant.Constants.NO_INTERNET_ERROR
import com.cronpay.cronpaysdkexample.model.ApiResponse
import com.cronpay.cronpaysdkexample.model.request.LoginRequest
import com.cronpay.cronpaysdkexample.service.ExampleService
import com.cronpay.cronpaysdkexample.util.ResponseWrapper
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val exampleService: ExampleService,) : AuthRepository {

    private var isLoading = MutableLiveData<Boolean>()

    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override  suspend fun getAccessToken(loginRequest:LoginRequest): ResponseWrapper<ApiResponse> {
        return try {
            loading()
            val response = exampleService.getAuthToken(loginRequest)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let  ResponseWrapper.success(it)
                } ?: ResponseWrapper.error(ERROR_MESSAGE, null)
            } else {
                loaded()
                ResponseWrapper.error(ERROR_MESSAGE, null)
            }
        } catch(e: Exception) {
            loaded()
            ResponseWrapper.error(NO_INTERNET_ERROR, null)
        }
    }
    override fun stopLoading(){
        loaded()
    }

    private fun  loaded() {
        isLoading.value = false
    }

    private fun  loading() {
        isLoading.value = true
    }

}
