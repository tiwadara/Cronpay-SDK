package com.cronpay.cronpaysdkexample

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.cronpay.cronpaysdk.CronPaySDK
import com.cronpay.cronpaysdkexample.model.request.LoginRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var accessKey: String? = ""
    private val viewModel: ExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.getAccessToken(LoginRequest(username = "teewah24@gmail.com", password = "tested12"))?.observe(this, {
            if (it != null) {
                accessKey =  it.data?.data?.access_token
                CronPaySDK.initialize(this, accessKey)
                Log.e("^^^^^^^^^^", "onCreate: $accessKey", )
            }
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
          CronPaySDK.startMandate(object : CronPaySDK.CronPayListener{
              override fun onError(error: String) {
                  Log.e("*#*#*#*#* Errror", "onCreate: $error", )
              }

              override fun onSuccess(message: String) {
                  Log.e("*#*#*#*#* success", "onCreate: $message", )
              }

              override fun onClose() {

              }

          })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
