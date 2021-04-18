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

object CronPayFlutterEngine {

    /**
     * Application COntext
     */
    var applicationContext: Context? = null

    private lateinit var flutterEngine: FlutterEngine
    private  val FLUTTER_ENGINE_ID = "cronpay_flutter_engine_id"
    private val CHANNEL = "com.tiwa.cronpayanndroidhost/test"
    private val METHOD_DIRECT_DEPOSIT = "dp"
    private val METHOD_CARD = "card"
    private val METHOD_INITIALIZE = "initialize"
    private val  METHOD_SEND_SUCCESS_CALLBACK= "send_success_call_back"
    private val  METHOD_SEND_CLOSE_CALLBACK= "send_close_call_back"
    private var accessKey ="sdsd"


    fun launchEngine(){
        initFlutterEngine(applicationContext!!, accessKey)
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_ID, flutterEngine)
        launchFlutterModule()
    }

    fun initFlutterEngine(context: Context, accessToken: String?) {
        this.accessKey = accessToken!!
        this.applicationContext = context
        flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault())
        setupMethodChannel()
    }

    private fun setupMethodChannel() {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when {
                call.method.equals(METHOD_INITIALIZE) -> {
                    Log.e("initialize request",call.method + call.arguments.toString() + accessKey);
                    result.success(accessKey)

                }
                call.method.equals(METHOD_SEND_CLOSE_CALLBACK) -> {
                    Log.e("callback",call.method);

                }
                call.method.equals(METHOD_SEND_SUCCESS_CALLBACK) -> {
                    Log.e("callback",call.method);

                }
                else -> {
                    result.notImplemented()
                    Log.e("new method came",call.method);
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