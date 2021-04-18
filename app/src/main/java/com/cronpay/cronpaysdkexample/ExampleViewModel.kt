package com.cronpay.cronpaysdkexample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cronpay.cronpaysdkexample.model.ApiResponse
import com.cronpay.cronpaysdkexample.model.request.LoginRequest
import com.cronpay.cronpaysdkexample.repository.AuthRepositoryImpl
import com.cronpay.cronpaysdkexample.util.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(private val repository: AuthRepositoryImpl): ViewModel() {
    private var  responseLiveData =  MutableLiveData<ResponseWrapper<ApiResponse>>()

    fun getAccessToken(loginRequest: LoginRequest): LiveData<ResponseWrapper<ApiResponse>>? {
        var response : ResponseWrapper<ApiResponse>? = null
        viewModelScope.launch {
            response =   repository.getAccessToken(loginRequest)
            responseLiveData.value = response
        }.invokeOnCompletion {
            responseLiveData.value = response
        }

        return responseLiveData
    }
}