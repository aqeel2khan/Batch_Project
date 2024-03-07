package com.dev.batchfinal.view.account

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.databinding.ActivitySplashBinding
import com.dev.batchfinal.session.UserSessionManager
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private lateinit var  sessionManager: UserSessionManager

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
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

                /* if (!sharedPreferences.gender!!.isNullOrEmpty()){
                 startActivity(Intent(this@SplashActivity, MainActivity::class.java).addFlags(
                         Intent.FLAG_ACTIVITY_NEW_TASK
                     ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                     )
                     finish()
                 }else{
                     startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java).addFlags(
                         Intent.FLAG_ACTIVITY_NEW_TASK
                     ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                     )
                     finish()
                 }
 *///Commented BBh
                /*if (sharedPreferences.token != null){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()
                }else{
                    startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()
                }*/
            }, 2000)
        }
    }

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)
}