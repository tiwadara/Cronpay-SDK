package com.cronpay.cronpaysdk

import android.app.Activity
import android.content.Context


/**
 * This is the  croppay sdk manager class.
 * Must be used to initialize the Sdk.
 *
 * @author {Tiwadara} on 0/0/1990.
 */
object CronPaySDK {

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
    @Synchronized
    fun initialize(context: Context) {
        initialize(context)
    }
}