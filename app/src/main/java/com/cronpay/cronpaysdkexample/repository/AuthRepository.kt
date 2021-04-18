package com.cronpay.cronpaysdkexample.repository

import androidx.lifecycle.LiveData
import com.cronpay.cronpaysdkexample.model.ApiResponse
import com.cronpay.cronpaysdkexample.model.request.LoginRequest
import com.cronpay.cronpaysdkexample.util.ResponseWrapper


interface AuthRepository {
    fun isLoading(): LiveData<Boolean>
    suspend fun getAccessToken(loginRequest: LoginRequest): ResponseWrapper<ApiResponse>
    fun stopLoading()
}