package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import androidx.activity.viewModels
import com.dev.batchfinal.databinding.ActivityVerificationBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationActivity : BaseActivity<ActivityVerificationBinding>() {
    private val viewModel: AllViewModel by viewModels()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        binding.verificationButton.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityVerificationBinding.inflate(layoutInflater)
}