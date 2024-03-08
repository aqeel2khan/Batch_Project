package com.dev.batchfinal.app_utils

import android.util.Log

class LogUtil {

    companion object
    {
        fun showLog(logTitle:String,logMessage:String)
        {
            Log.e(logTitle,logMessage)
        }

    }
}