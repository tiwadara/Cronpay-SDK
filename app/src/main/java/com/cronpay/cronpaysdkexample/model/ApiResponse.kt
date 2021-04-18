package com.cronpay.cronpaysdkexample.model

data class ApiResponse(
    val `data`: Data,
    val message: Any,
    val status: Int
)