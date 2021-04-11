package com.cronpay.cronpaysdk

import android.content.Context


/**
 * This is the  croppay sdk manager class.
 * Must be used to initialize the Sdk.
 *
 * @author {Tiwadara} on 0/0/1990.
 */
object CronPaySDK {

    /**
     * Application COntext
     */
    private var applicationContext: Context? = null

    /**
     * Flag to know if sdk has been initialized
     */
    var isSdkInitialized = false
        private set

    /**
     * Reference to the public key
     */
    @Volatile
    private var publicKey: String? = null

    /**
     * Initialize an sdk
     *
     * @param context - Application Context
     */
    fun initialize(context: Context?) {
        if (context != null) {
            applicationContext = context
            isSdkInitialized = true
            CronPayFlutterEngine.initFlutterEngine(applicationContext!!)
        }
    }

    @Throws(java.lang.NullPointerException::class)
    fun startMandate() {
        if (isSdkInitialized){
            CronPayFlutterEngine.launchEngine()
        } else {
            throw NullPointerException()
        }
    }
}