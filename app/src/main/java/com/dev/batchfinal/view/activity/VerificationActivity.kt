package com.dev.batchfinal.view.activity

import android.content.Intent
import androidx.activity.viewModels
import com.dev.batchfinal.databinding.ActivityVerificationBinding
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.view.account.ExploreActivity
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
       /* binding.editOne.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().trim().isEmpty()){
                    binding.editTwo.setBackgroundResource(R.drawable.edit_bg);
                } else {
                    binding.editTwo.setBackgroundResource(R.drawable.form_shape)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
*/
    }

    override fun getViewBinding() = ActivityVerificationBinding.inflate(layoutInflater)
}