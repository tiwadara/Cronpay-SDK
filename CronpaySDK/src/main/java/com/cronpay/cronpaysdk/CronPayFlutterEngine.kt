package com.cronpay.cronpaysdk

import android.content.Context
import android.content.Intent
import android.util.Log
import io.flutter.embedding.android.FlutterActivity.withCachedEngine
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

internal object CronPayFlutterEngine {

    /**
     * Application COntext
     */
    var applicationContext: Context? = null

    private lateinit var flutterEngine: FlutterEngine
    private val FLUTTER_ENGINE_ID = "cronpay_flutter_engine_id"
    private val CHANNEL = "com.cronpay.cronpaysdk/mainChannel"
    private val METHOD_DIRECT_DEPOSIT = "dp"
    private val METHOD_CARD = "card"
    private val METHOD_INITIALIZE = "initialize"
    private val METHOD_MANDATE_CREATED_CALLBACK= "mandate_created_call_back"
    private val METHOD_SEND_CLOSE_CALLBACK = "send_close_call_back"
    private val METHOD_ERROR_CALLBACK = "error_call_back"
    private var accessKey = ""


    fun launchEngine(accessToken: String?, context: Context?) {
        this.accessKey = accessToken!!
        this.applicationContext = context
        initFlutterEngine(this.applicationContext!!)
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_ID, flutterEngine)
        launchFlutterModule()
    }

    fun initFlutterEngine(context: Context) {
        flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault())
        setupMethodChannel()
    }

    private fun setupMethodChannel() {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when {
                call.method.equals(METHOD_INITIALIZE) -> {
                    result.success(accessKey)

                }
                call.method.equals(METHOD_SEND_CLOSE_CALLBACK) -> {
                    CronPaySDK.onClose()

                }
                call.method.equals(METHOD_MANDATE_CREATED_CALLBACK) -> {
                    CronPaySDK.onSuccess(call.arguments.toString())

                }
                call.method.equals(METHOD_ERROR_CALLBACK) -> {
                    CronPaySDK.onError(call.arguments.toString())

                }
                else -> {
                    result.notImplemented()
                    Log.e("new method came", call.method);
                }
            }
        }
    }

    private fun launchFlutterModule() {
        applicationContext?.startActivity(getFlutterIntent())
    }

    private fun getFlutterIntent(): Intent {
        return withCachedEngine(FLUTTER_ENGINE_ID)
                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                .build(applicationContext!!)
    }
}