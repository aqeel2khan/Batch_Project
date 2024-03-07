package com.dev.batchfinal.view.account

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.custom.CustomToast.Companion.showToast
import com.dev.batchfinal.databinding.ActivityLoginBinding
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.session.UserSessionManager
import com.dev.batchfinal.utils.CheckNetworkConnection
import com.dev.batchfinal.utils.MyConstant
import com.dev.batchfinal.utils.MyConstant.jsonObject
import com.dev.batchfinal.utils.MyConstant.success
import com.dev.batchfinal.utils.MyCustom.errorBody
import com.dev.batchfinal.utils.MyUtils
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var  sessionManager:UserSessionManager

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initUi() {
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().trim().isEmpty()) {
                    binding.signButton.setBackgroundResource(R.drawable.rectangle_button_gry);
//                    binding.signButton.visibility = View.VISIBLE
//                    binding.btnSignInSel.visibility = View.GONE
                } else {
                    binding.signButton.setBackgroundResource(R.drawable.rectangle_button)
//                    binding.btnSignInSel.visibility = View.VISIBLE
//                    binding.signButton.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.signButton.setOnClickListener {
            validation()
        }
        binding.createAccount.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.passHideShow.setOnClickListener {
            MyUtils.passwordShowHide(binding, MyConstant.newPassword)
        }
        binding.termsCondition.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView: View = layoutInflater.inflate(R.layout.terms_conditions, null)
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.show()
            val closeButton = bottomSheetView.findViewById<Button>(R.id.agreeAndContinueButton)
            closeButton.setOnClickListener {
                bottomSheetDialog.dismiss()
                showToast("you are agree this condition")
            }
        }
        binding.backButtonLogin.setOnClickListener {
            finish()
        }
    }

    private fun validation() {
        if (binding.emailEditText.text.toString().isEmpty()) {
            showToast("Please enter Email")
        } else if (!MyUtils.isValidEmail(binding.emailEditText.text.toString())) {
            showToast("Enter Valid Email Address")
        } else if (binding.password.text.toString().isEmpty()) {
            showToast("Enter Password")
        } else {
            jsonObject.addProperty("email", binding.emailEditText.text?.trim().toString())
            jsonObject.addProperty("password", binding.password.text?.trim().toString())
            jsonObject.addProperty("device_token", getDeviceIds().toString())
            Log.e("DEVICE_ID",getDeviceIds().toString())
            loginApi(jsonObject)
        }
    }

    private fun loginApi(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.loginResponseApi(jsonObject)
            authViewModel.loginResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.loginResponse.removeObservers(this)
                        if (authViewModel.loginResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == success) {

                                    // sharedPreferences.saveToken(response.token.toString())
                                    //sharedPreferences.saveEmail(response.data.email.toString())
                                    //  sharedPreferences.saveUserId(response.data.id.toString())
                                    sessionManager=UserSessionManager(this@LoginActivity)
                                    sessionManager.createUserSession(
                                        ""+response.data.id.toString(),
                                        response.token,
                                        ""+response.data.mobile,
                                        ""+response.data.email, ""+response.data.name, true
                                    )

                                   //  startActivity(Intent(Intent(this@LoginActivity, VerificationActivity::class.java)))
                                    // startActivity(Intent(Intent(this@LoginActivity, CheckOutActivity::class.java)))//Commented @BBh
                                    startActivity(Intent(Intent(this@LoginActivity, MainActivity::class.java)))


                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.loginResponse.removeObservers(this)
                        if (authViewModel.loginResponse.hasObservers()) return@observe
                        hideLoader()
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {

                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
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