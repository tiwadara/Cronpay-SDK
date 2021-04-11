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
    private  val FLUTTER_ENGINE_ID = "my_engine_id"
    private val CHANNEL = "com.tiwa.cronpayanndroidhost/test"
    private val METHOD_DIRECT_DEPOSIT = "dp"
    private val METHOD_CARD = "card"
    private val METHOD_INITIALIZE = "initialize"


    fun launchEngine(){
        initFlutterEngine(applicationContext!!)
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_ID, flutterEngine)
        launchFlutterModule()
        setupMethodChannel()
    }

    fun initFlutterEngine(context: Context) {
        this.applicationContext = context
        flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault())
    }

    private fun setupMethodChannel() {
        MethodChannel(
                flutterEngine.dartExecutor.binaryMessenger,
                CHANNEL
        ).setMethodCallHandler { call, result ->
            if(call.method.equals (METHOD_CARD))
            {
                result.success("Hai from android and this is the data yopu sent me "+ call.argument("data"));

            }
            else
            {
                Log.e("new method came",call.method);
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