package com.dev.batchfinal

import android.app.Application
import com.dev.batchfinal.utils.Config
import com.myfatoorah.sdk.enums.MFCountry
import com.myfatoorah.sdk.enums.MFEnvironment
import com.myfatoorah.sdk.views.MFSDK
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.io.File

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        Timber.plant(Timber.DebugTree())

        // set up your My Fatoorah Merchant details
        MFSDK.init(Config.API_KEY, MFCountry.KUWAIT, MFEnvironment.TEST)
    }
}