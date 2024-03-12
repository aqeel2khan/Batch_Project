package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_custom.CustomToast.Companion.showToast
import com.dev.batchfinal.databinding.ActivityRegistrationBinding
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_utils.MyUtils.isMobileNumberValid
import com.dev.batchfinal.app_utils.MyUtils.isValidEmail
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_modules.account.viewmodel.AccountFactoryModel
import com.dev.batchfinal.app_modules.account.viewmodel.AccountViewModel
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.CheckOutActivity
import com.dev.batchfinal.databinding.ActivityProfileBinding
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class RegistrationActivity : AppBaseActivity<ActivityRegistrationBinding>() {


    private lateinit var  sessionManager:UserSessionManager
    private lateinit var mViewModel: AccountViewModel
    private val retrofitService = AccountNetworkService.create()

    override fun getViewBinding() = ActivityRegistrationBinding.inflate(layoutInflater)

    override fun initUI() {
        sessionManager = UserSessionManager(this@RegistrationActivity)

        onCLickOperation()
    }

    override fun onStart() {
        super.onStart()
        mViewModel = ViewModelProvider(
            this,
            AccountFactoryModel(AccountRepository(retrofitService))
        )[AccountViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()

        mViewModel.getSignUpResponse.observe(this, Observer {
            try {
                hideLoader()
                sessionManager.createUserSession(
                    ""+it.data!!.id.toString(),
                    it.token.toString(),
                    ""+it.data!!.mobile,
                    ""+it.data!!.email, ""+it.data!!.name,
                    ""+it.data!!.dob, ""+it.data!!.gender,
                    "",true
                )

                startActivity(Intent(Intent(this@RegistrationActivity, MainActivity::class.java)))
            }catch (_:Exception){startActivity(Intent(Intent(this@RegistrationActivity, MainActivity::class.java))) }

        })

        mViewModel.errorMessage.observe(this, Observer {
            showAlertInfo(it.toString(), this)
            hideLoader()
        })


    }

    private fun onCLickOperation() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.btnGoToCheckout.setOnClickListener {
            requestSignUp()
        }
        binding.passHideShow.setOnClickListener {
            MyUtils.passwordShowHide(binding, MyConstant.newPassword)
        }
        binding.emailEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().trim().isEmpty()){
                    binding.btnGoToCheckout.setBackgroundResource(R.drawable.rectangle_button_gry)
                } else {
                    binding.btnGoToCheckout.setBackgroundResource(R.drawable.rectangle_button)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.onCLickBack.setOnClickListener {
            finish()
        }

    }

    private fun requestSignUp() {
        when {
            binding.fullNameEditText.text.toString().isEmpty() -> {
                showAlertInfo("Please enter Full Name",this)
            }
            binding.phoneEditText.text.toString().isEmpty() -> {
                showAlertInfo("Please enter Mobile Number",this)

            }
            !isMobileNumberValid(binding.phoneEditText.text.toString()) -> {
                showAlertInfo("Enter Valid Mobile Number",this)
            }
            binding.emailEditText.text.toString().isEmpty() -> {
                showAlertInfo("Please enter Email",this)

            }
            !isValidEmail(binding.emailEditText.text.toString()) -> {
                showAlertInfo("Enter Valid Email Address",this)

            }
            binding.password.text.toString().isEmpty() -> {
                showAlertInfo("Please enter Password",this)
            }
            !binding.agreeCheckBox.isChecked -> {
                showToast("Please Agree Terms & Conditions")
            }
            else -> {

                if (checkNetwork(this)) {
                    showLoader()
                    mViewModel.requestSignUp(
                        binding.emailEditText.text.toString(),
                        binding.password.text.toString(),
                        binding.phoneEditText.text.toString(),
                        binding.fullNameEditText.text.toString()
                    )
                } else {
                    showAlertInfo("Please check internet connection", this)
                }


            }
        }
    }




    private fun setDividerWidth(divider: View) {
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val screenWidth = display.width
        divider.layoutParams.width = screenWidth / 4.1.toInt()
        divider.requestLayout()
    }
}