package com.cronpay.cronpaysdkexample.util


data class ResponseWrapper<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResponseWrapper<T> {
            return ResponseWrapper(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResponseWrapper<T> {
            return ResponseWrapper(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResponseWrapper<T> {
            return ResponseWrapper(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}