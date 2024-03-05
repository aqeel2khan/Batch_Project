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
import com.example.bottomanimationmydemo.databinding.ActivityLoginBinding
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyConstant.jsonObject
import com.example.bottomanimationmydemo.utils.MyConstant.success
import com.example.bottomanimationmydemo.utils.MyCustom.errorBody
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.utils.NetworkErrorResult
import com.example.bottomanimationmydemo.utils.snackBarWithRedBackground
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }
    override fun initUi() {
       buttonClicks()
    }

    private fun buttonClicks() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().trim().isEmpty()){
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
        binding.termsCondition.setOnClickListener {  val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView: View = layoutInflater.inflate(R.layout.terms_conditions, null)
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.show()
            val closeButton = bottomSheetView.findViewById<Button>(R.id.agreeAndContinueButton)
            closeButton.setOnClickListener {
                bottomSheetDialog.dismiss()
                showToast("you are agree this condition")
            } }
    }

    private fun validation() {
        if (binding.emailEditText.text.toString().isEmpty()) {
            showToast("Please enter Email")
        } else if (!MyUtils.isValidEmail(binding.emailEditText.text.toString())) {
            showToast("Enter Valid Email Address")
        }else if(binding.password.text.toString().isEmpty()){
            showToast("Enter Password")
        }else{
            jsonObject.addProperty("email",binding.emailEditText.text?.trim().toString())
            jsonObject.addProperty("password",binding.password.text?.trim().toString())
            jsonObject.addProperty("device_token","AASddd")
            loginApi(jsonObject)
        }
    }

    private fun loginApi(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this,binding.root, true)) {
            showLoader()
            authViewModel.loginResponseApi(jsonObject)
            authViewModel.loginResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.loginResponse.removeObservers(this)
                        if (authViewModel.loginResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == success){

                                    sharedPreferences.saveToken(response.token.toString())
                                    sharedPreferences.saveEmail(response.data.email.toString())
                                    sharedPreferences.saveUserId(response.data.id.toString())

                                    startActivity(Intent(Intent(this@LoginActivity, CheckOutActivity::class.java)))
//                                    startActivity(Intent(Intent(this@LoginActivity, VerificationActivity::class.java)))

                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.loginResponse.removeObservers(this)
                        if (authViewModel.loginResponse.hasObservers()) return@observe
                        hideLoader()
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }

    }

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    private fun setDividerWidth(divider: View) {
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val screenWidth = display.width
        divider.layoutParams.width = screenWidth / 4.1.toInt()
        divider.requestLayout()
    }
}