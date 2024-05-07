package com.anhht.google

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData

class
BaseNetworkConnectionObserver(context: Context) : LiveData<Boolean>() {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkConnectionCallback: ConnectivityManager.NetworkCallback? = null
    var isLost = false

    fun isOnline(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
            netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()

        try {
            updateNetworkConnection()
            connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())

        } catch (e: Exception) {
            Log.d("ExceptionMsg", e.toString())
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (networkConnectionCallback != null) {
            try {
                connectivityManager.unregisterNetworkCallback(networkConnectionCallback!!)
                // Added code: Set call back to null so this doesn't get called again.
                networkConnectionCallback = null
            } catch (e: java.lang.Exception) {
                Log.d("ExceptionMsg", e.toString())
            }
        }
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        networkConnectionCallback = object : ConnectivityManager.NetworkCallback() {
            @SuppressLint("MissingPermission")

            override fun onLost(network: Network) {
                super.onLost(network)

                postValue(false)
                isLost = true
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                if (isLost) {
                    postValue(true)
                }
                isLost = false
            }
        }
        return networkConnectionCallback as ConnectivityManager.NetworkCallback
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                updateNetworkConnection()
            } catch (e: Exception) {
                Log.d("BroadcastOnReceiver", e.toString())
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun updateNetworkConnection() {
        try {
            val activeNetworkConnection: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (isLost) {
                postValue(activeNetworkConnection?.isConnected == true)
            }
        } catch (e: Exception) {
            Log.d("ExceptionMsg", e.toString())
        }
    }
}