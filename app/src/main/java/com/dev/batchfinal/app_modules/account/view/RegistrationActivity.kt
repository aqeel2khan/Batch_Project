package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
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
import com.dev.batchfinal.app_modules.activity.CheckOutActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class RegistrationActivity : BaseActivity<ActivityRegistrationBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
   // private var strValue: String? = null
    private lateinit var  sessionManager:UserSessionManager

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
       // strValue = intent.getStringExtra("batchMeal")
       buttonClicks()
    }

    private fun buttonClicks() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.btnGoToCheckout.setOnClickListener {
            validation()
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

    }

    private fun validation() {
        if (binding.fullNameEditText.text.toString().isEmpty()) {
            showToast("Please enter Full Name")
        } else if (binding.phoneEditText.text.toString().isEmpty()) {
            showToast("Please enter Mobile")
        } else if (!isMobileNumberValid(binding.phoneEditText.text.toString())) {
            showToast("Enter Valid Mobile Number")
        }else if (binding.emailEditText.text.toString().isEmpty()) {
            showToast("Please enter Email")
        } else if (!isValidEmail(binding.emailEditText.text.toString())) {
            showToast("Enter Valid Email Address")
        }
        else if (!binding.agreeCheckBox.isChecked) {
            showToast("Please Agree Terms & Conditions")
        } else {
            MyConstant.jsonObject.addProperty("email",binding.emailEditText.text?.trim().toString())
            MyConstant.jsonObject.addProperty("password",binding.password.text?.trim().toString())
            MyConstant.jsonObject.addProperty("mobile",binding.phoneEditText.text?.trim().toString())
            MyConstant.jsonObject.addProperty("name",binding.fullNameEditText.text?.trim().toString())
            signUp(MyConstant.jsonObject)
        }
    }

    private fun signUp(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this,binding.root, true)) {
            showLoader()
            authViewModel.signUpApiCall(jsonObject)
            authViewModel.signUpResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.signUpResponse.removeObservers(this)
                        if (authViewModel.signUpResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success){

                                   // sharedPreferences.saveToken(response.token.toString())
                                    sessionManager= UserSessionManager(this@RegistrationActivity)
                                    sessionManager.createUserSession(
                                        ""+response.data.id.toString(),
                                        response.token,
                                        ""+response.data.mobile,
                                        ""+response.data.email, ""+response.data.name,
                                        ""+response.data.dob, ""+response.data.gender,
                                        "",true
                                    )

                                    if (!intent.getStringExtra("batchMeal").isNullOrEmpty())
                                    startActivity(Intent(this@RegistrationActivity, CheckOutActivity::class.java).putExtra("screen", intent.getStringExtra("batchMeal").toString()))
                                    else
                                        startActivity(Intent(Intent(this@RegistrationActivity, MainActivity::class.java)))
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.signUpResponse.removeObservers(this)
                        if (authViewModel.signUpResponse.hasObservers()) return@observe
                        hideLoader()
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    override fun getViewBinding() = ActivityRegistrationBinding.inflate(layoutInflater)

    private fun setDividerWidth(divider: View) {
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val screenWidth = display.width
        divider.layoutParams.width = screenWidth / 4.1.toInt()
        divider.requestLayout()
    }
}