package com.example.climafilm.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

interface NetworkChecker {
    fun hasInternetConnection(): Boolean
}

class NetworkCheckerImpl @Inject constructor(
    private val context: Context
) : NetworkChecker {
    override fun hasInternetConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}