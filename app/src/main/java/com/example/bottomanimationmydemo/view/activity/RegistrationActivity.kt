package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityRegistrationBinding
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.utils.MyUtils.isMobileNumberValid
import com.example.bottomanimationmydemo.utils.MyUtils.isValidEmail
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

@AndroidEntryPoint
class RegistrationActivity : BaseActivity<ActivityRegistrationBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var strValue: String? = null
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        strValue = intent.getStringExtra("batchMeal")
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

                                    sharedPreferences.saveToken(response.token.toString())
                                    startActivity(Intent(this@RegistrationActivity, CheckOutActivity::class.java).putExtra("screen", strValue))
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