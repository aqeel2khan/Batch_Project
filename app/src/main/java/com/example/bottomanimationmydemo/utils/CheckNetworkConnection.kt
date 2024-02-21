package com.example.bottomanimationmydemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import com.example.bottomanimationmydemo.R

object CheckNetworkConnection {

    fun isConnection(context: Context, view: View, toast: Boolean = true): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw      = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
   /* fun isConnection(context: Context, toast: Boolean = true): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val conn = connectivityManager.activeNetworkInfo
        return if (conn != null && conn.isAvailable && conn.isConnected){
            true
        }else{
            if (toast)
                Toast.makeText(context, R.string.please_check_internet, Toast.LENGTH_LONG).show()
            false
        }
    }*/
}