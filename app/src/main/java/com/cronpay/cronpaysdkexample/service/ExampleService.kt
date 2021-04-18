package com.cronpay.cronpaysdkexample.service

import com.cronpay.cronpaysdkexample.constant.Constants
import com.cronpay.cronpaysdkexample.model.ApiResponse
import com.cronpay.cronpaysdkexample.model.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ExampleService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(Constants.LOGIN)
    suspend fun getAuthToken(@Body loginRequest: LoginRequest ): Response<ApiResponse>
}