package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.databinding.ActivitySplashBinding
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppBaseActivity<ActivitySplashBinding>() {
    private lateinit var  sessionManager: UserSessionManager

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initUI() {
        sessionManager= UserSessionManager(this@SplashActivity)
        binding.apply {
            Handler(Looper.myLooper()!!).postDelayed({

                if (sessionManager.isloggin()){
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                    finish()
                }else{
                    if (getSetting("GENDER","")!!.isNotEmpty())
                    {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        )

                    }else
                    {
                        startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        )

                    }
                    finish()
                }


            }, 2000)
        }
    }

}