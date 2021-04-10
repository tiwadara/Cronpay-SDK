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

class CronPayFlutterEngine {

    private lateinit var flutterEngine: FlutterEngine
    private  val FLUTTER_ENGINE_ID = "my_engine_id"
    private val CHANNEL = "com.tiwa.cronpayanndroidhost/test"
    private val METHOD_DIRECT_DEPOSIT = "dp"
    private val METHOD_CARD = "card"
    private val METHOD_INITIALIZE = "initialize"
    private var context: Context? = null

    fun launchEngine(context: Context) {
        this.context = context
        launchFlutterModule()
    }

    fun setupFlutterEngine(context: Context) {
        this.context = context
        createAndConfigureFlutterEngine()
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_ID, flutterEngine)
        setupMethodChannel()
    }

    private fun createAndConfigureFlutterEngine() {
        flutterEngine = FlutterEngine(context!!)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        flutterEngine.navigationChannel.setInitialRoute("/createMandate'")
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
        context?.startActivity(getFlutterIntent())
    }

    private fun getFlutterIntent(): Intent {
        return withCachedEngine(FLUTTER_ENGINE_ID)
                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                .build(context!!)
    }
}