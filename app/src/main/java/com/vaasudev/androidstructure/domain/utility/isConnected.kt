package com.vaasudev.androidstructure.domain.utility

import android.content.Context
import android.net.ConnectivityManager

fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val cap = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        cap.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> true
        cap.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}