package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_custom.CustomToast.Companion.showToast
import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_modules.account.viewmodel.AccountFactoryModel
import com.dev.batchfinal.app_modules.account.viewmodel.AccountViewModel
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.CheckOutActivity
import com.dev.batchfinal.databinding.ActivityLoginBinding
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppBaseActivity<ActivityLoginBinding>() {
    private lateinit var mViewModel: AccountViewModel
    private val retrofitService = AccountNetworkService.create()
    private lateinit var sessionManager: UserSessionManager
    var screen: String? = null
    var product_id: String? = null

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)
    override fun initUI() {
        sessionManager = UserSessionManager(this@LoginActivity)
        screen = intent.getStringExtra("screen")
        product_id = intent.getStringExtra("product_id")
        onCickOperation()
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
        mViewModel.getLoginDetails.observe(this, Observer {
            hideLoader()
            showToastLong("Login Successfully", this)
            sessionManager.createUserSession(
                "" + it.data!!.id.toString(), it!!.token.toString(),
                "" + it.data!!.mobile, "" + it.data!!.email,
                "" + it.data!!.name,it.data!!.dob.toString(),
                it.data!!.gender.toString(),it.data!!.profilePhotoPath.toString(),true
            )
            if(screen.equals("workout_batch")){
                startActivity(
                    Intent(this@LoginActivity, CheckOutActivity::class.java)
                        .putExtra("screen", screen)
                        .putExtra("product_id", product_id))
                finish()
            }else if(screen.equals("meal_batch")){
                startActivity(
                    Intent(this@LoginActivity, CheckOutActivity::class.java)
                        .putExtra("screen", screen)
                        .putExtra("product_id", product_id))
                finish()
            }else{
                startActivity(Intent(Intent(this@LoginActivity, MainActivity::class.java)))
                finish()
            }


            dismissProgress()
        })
        mViewModel.errorMessage.observe(this, Observer {
            showAlertInfo(it.toString(), this)
            hideLoader()
        })
    }
    private fun onCickOperation() {

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    s.toString().trim().isEmpty() -> {
                        binding.signButton.setBackgroundResource(R.drawable.rectangle_button_gry);
                    }
                    else -> {
                        binding.signButton.setBackgroundResource(R.drawable.rectangle_button)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.createAccount.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.passHideShow.setOnClickListener {
            MyUtils.passwordShowHide(binding, MyConstant.newPassword)
        }
        binding.termsCondition.setOnClickListener {
         showBottomShenTnC()
        }
        binding.signButton.setOnClickListener {
            requestLogin()
        }

        binding.backButtonLogin.setOnClickListener {
            finish()
        }
    }


    private fun requestLogin() {
        when {
            binding.emailEditText.text.toString().isEmpty() -> {
                showAlertInfo("Please enter email-id", this)
            }
            !MyUtils.isValidEmail(binding.emailEditText.text.toString()) -> {
                showAlertInfo("Please enter valid email address", this)
            }
            binding.password.text.toString().isEmpty() -> {
                showAlertInfo("Please enter password", this)
            }
            !binding.agreeCheckBox.isChecked -> {
                showAlertInfo("Please follow the Term & Conditions.", this)
            }
            else -> {
                if (checkNetwork(this))
                {
                    showLoader()
                    mViewModel.requestLogin(
                        binding.emailEditText.text?.trim().toString(),
                        binding.password.text?.trim().toString(),
                        getDeviceIds().toString()
                    )
                }else
                {
                    showAlertInfo("Please check internet connection",this)
                }

            }
        }
    }
    private fun showBottomShenTnC() {
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


    private fun setDividerWidth(divider: View) {
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val screenWidth = display.width
        divider.layoutParams.width = screenWidth / 4.1.toInt()
        divider.requestLayout()
    }
}