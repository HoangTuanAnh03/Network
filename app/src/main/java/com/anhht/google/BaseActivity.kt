


package com.anhht.google

import android.content.BroadcastReceiver
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.anhht.google.databinding.NetworkErrorBinding
import com.anhht.google.databinding.NetworkOfflineOnlineLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        val tag: String = BaseActivity::class.java.simpleName
    }

    // network connection
    private lateinit var networkErrorWithDisableAllViews: LinearLayout
    private lateinit var networkBackOnline: LinearLayout
    private lateinit var bottomSheetDialogOffline: BottomSheetDialog
    private lateinit var bottomSheetDialogOnline: BottomSheetDialog
    private lateinit var networkConnection: BaseNetworkConnectionObserver



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkConnection = BaseNetworkConnectionObserver(this)

        if (!networkConnection.isOnline(this)) {
            networkConnection.isLost = true
        }


        networkConnection.observe(this) { isConnected ->
            if (isConnected != null) {
                if (isConnected) {
                    isNetworkConnectionOn(this@BaseActivity)
//                    isInternetOnCallApisAndInitUI()
                } else {
                    isNetworkConnectionOff(this@BaseActivity)
                }
            }
        }
    }

    //    abstract fun isInternetOnCallApisAndInitUI()

    private fun isNetworkConnectionOff(context: Context) {
        bottomSheetDialogOffline = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val binding = NetworkOfflineOnlineLayoutBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialogOffline.setContentView(binding.root)
        bottomSheetDialogOffline.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        networkErrorWithDisableAllViews = binding.disableAllViews
        networkBackOnline = binding.layoutNetworkBackOnline

        DialogUtils.showNetworkError(networkErrorWithDisableAllViews, networkBackOnline)

        bottomSheetDialogOffline.setCancelable(false)
        bottomSheetDialogOffline.show()
    }

    private fun isNetworkConnectionOn(context: Context) {

        bottomSheetDialogOffline.dismiss()

        bottomSheetDialogOnline = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val binding = NetworkOfflineOnlineLayoutBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialogOnline.setContentView(binding.root)
        bottomSheetDialogOnline.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        networkErrorWithDisableAllViews = binding.disableAllViews
        networkBackOnline = binding.layoutNetworkBackOnline

        DialogUtils.showNetworkBackOnline(networkErrorWithDisableAllViews, networkBackOnline)

        DialogUtils.enableDisableView(binding.root, true)

        bottomSheetDialogOnline.setCancelable(true)
        bottomSheetDialogOnline.show()

        Handler(Looper.getMainLooper()).postDelayed(5000) {
            bottomSheetDialogOnline.dismiss()
        }
    }
}