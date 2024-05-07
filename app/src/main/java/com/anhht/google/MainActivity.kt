package com.anhht.google


import android.os.Bundle

import com.anhht.google.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }
//    override fun isInternetOnCallApisAndInitUI() {
//        // here re-call & re-init your api's and UI
//    }
}

//
//package com.anhht.google
//
//import android.content.BroadcastReceiver
//import android.content.IntentFilter
//import android.graphics.Color
//import android.net.ConnectivityManager
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.view.View
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//
//
//class MainActivity : AppCompatActivity() {
//    private var mNetworkReceiver: BroadcastReceiver? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        tv_check_connection = findViewById<View>(R.id.tv_check_connection) as TextView
//        mNetworkReceiver = NetworkChangeReceiver()
//        registerNetworkBroadcastForNougat()
//    }
//
//    private fun registerNetworkBroadcastForNougat() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            registerReceiver(
//                mNetworkReceiver,
//                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//            )
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            registerReceiver(
//                mNetworkReceiver,
//                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//            )
//        }
//    }
//
//    protected fun unregisterNetworkChanges() {
//        try {
//            unregisterReceiver(mNetworkReceiver)
//        } catch (e: IllegalArgumentException) {
//            e.printStackTrace()
//        }
//    }
//
//    public override fun onDestroy() {
//        super.onDestroy()
//        unregisterNetworkChanges()
//    }
//
//    companion object {
//        var tv_check_connection: TextView? = null
//        fun dialog(value: Boolean) {
//            if (value) {
//                tv_check_connection!!.text = "We are back !!!"
//                tv_check_connection!!.setBackgroundColor(Color.GREEN)
//                tv_check_connection!!.setTextColor(Color.WHITE)
//                val handler = Handler()
//                val delayrunnable = Runnable { tv_check_connection!!.visibility = View.GONE }
//                handler.postDelayed(delayrunnable, 3000)
//            } else {
//                tv_check_connection!!.visibility = View.VISIBLE
//                tv_check_connection!!.text = "Could not Connect to internet"
//                tv_check_connection!!.setBackgroundColor(Color.RED)
//                tv_check_connection!!.setTextColor(Color.WHITE)
//            }
//        }
//    }
//}