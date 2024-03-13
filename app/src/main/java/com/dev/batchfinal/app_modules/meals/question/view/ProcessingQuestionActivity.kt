package com.dev.batchfinal.app_modules.question.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.databinding.ActivityProcessingQuestionBinding
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import android.os.Handler
import com.dev.batchfinal.app_modules.activity.FoodPlanBasedOnQuestionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProcessingQuestionActivity : BaseActivity<ActivityProcessingQuestionBinding>() {
    private val viewModel: AllViewModel by viewModels()
    var i = 0
    private val progressMax = 100
    private val progressDelay: Long = 100 // Delay in milliseconds
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var avgCalPerDayStr: String? =null
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        avgCalPerDayStr = intent.getStringExtra("avgCalPerDay")
        startProgress()
        /*Handler().postDelayed(object : Runnable {
            override fun run() {
                // set the limitations for the numeric
                // text under the progress bar
                if (i <= 100) {
                    binding.progressText.text = "" + i
                    binding.progressBar.progress = i
                    i++
                    Handler().postDelayed(this, 200)
                } else {
                    Handler().removeCallbacks(this)
                }
            }
        }, 200)*/
    }

    private fun startProgress() {
        binding.progressBar.max = progressMax

        coroutineScope.launch {
            var progress = 0
            while (progress < progressMax) {
                delay(progressDelay)
                progress++
                binding.progressBar.progress = progress
            }
            redirectToSecondScreen()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun redirectToSecondScreen() {
        val intent = Intent(this, FoodPlanBasedOnQuestionActivity::class.java)
            intent.putExtra("avgCalPerDay", avgCalPerDayStr)
        startActivity(intent)
        finish() // Optional, to finish this activity after redirecting
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel() // Cancel coroutine when activity is destroyed
    }

    override fun getViewBinding() = ActivityProcessingQuestionBinding.inflate(layoutInflater)

}