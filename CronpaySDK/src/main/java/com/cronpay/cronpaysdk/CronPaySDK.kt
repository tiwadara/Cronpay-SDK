package com.cronpay.cronpaysdk

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs


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
    var applicationContext: Context? = null

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
            Log.e("COntext****", "initialize: $context", )
            applicationContext = context
            isSdkInitialized = true
            CronPayFlutterEngine().setupFlutterEngine(applicationContext!!)
        }
    }

    @Throws(java.lang.NullPointerException::class)
    fun startMandate() {
        if (isSdkInitialized){
            CronPayFlutterEngine().setupFlutterEngine(applicationContext!!)
            CronPayFlutterEngine().launchEngine(applicationContext!!)
        } else {
            throw NullPointerException()
        }
    }
}