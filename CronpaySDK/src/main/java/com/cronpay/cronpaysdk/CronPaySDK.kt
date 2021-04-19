package com.cronpay.cronpaysdk

import android.content.Context


/**
 * This is the  croppay sdk class.
 * Must be used to initialize the Sdk.
 *
 * @author {TiwaDara} on 0/0/1990.
 */
object CronPaySDK {

    /**
     * Application Context
     */
    private var applicationContext: Context? = null
    private var accessToken: String? = null
    private var listener: CronPayListener? = null
    private var isSdkInitialized = false


    /**
     * Initialize the sdk
     *
     * @param context - Application Context
     * @param token - Access Token
     */
    fun initialize(context: Context?, token: String?) {
        if (context != null && token != null) {
            applicationContext = context
            isSdkInitialized = true
            accessToken = token
            CronPayFlutterEngine.initFlutterEngine(applicationContext!!)
        }
    }

    /**
     * Start mandate creation
     *
     * @param listener - CronPay Listener
     * @throws NullPointerException
     */

    fun startMandate(listener: CronPayListener ) {
        this.listener = listener
        if (isSdkInitialized) {
            CronPayFlutterEngine.launchEngine(accessToken, applicationContext)
        } else {
            this.listener?.onError("SDK not Initialized")
        }
    }

    internal fun onError(error: String) {
        listener?.onError(error)
    }

    internal fun onSuccess(message: String) {
        listener?.onSuccess(message)
    }

    internal fun onClose() {
        listener?.onClose()
    }

    interface  CronPayListener {
        fun onError(error: String) {}
        fun onSuccess(message: String){}
        fun onClose() {}
    }

}
