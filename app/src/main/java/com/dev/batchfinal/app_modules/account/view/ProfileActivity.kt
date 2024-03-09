package com.dev.batchfinal.app_modules.account.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.FollowingListAdapter
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_modules.account.viewmodel.AccountFactoryModel
import com.dev.batchfinal.app_modules.account.viewmodel.AccountViewModel
import com.dev.batchfinal.databinding.ActivityProfileBinding
import com.dev.batchfinal.databinding.ProfileEditDialogBinding
import com.dev.batchfinal.app_session.UserSessionManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileActivity : AppBaseActivity<ActivityProfileBinding>() {
    private lateinit var mViewModel: AccountViewModel
    private val retrofitService = AccountNetworkService.create()
    private lateinit var profileEditBinding: ProfileEditDialogBinding
    private lateinit var sessionManager: UserSessionManager
    private var courseImg = ArrayList(listOf(R.drawable.profile_image, R.drawable.normal_boy, R.drawable.profile_image))
    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)

    override fun initUI() {
        sessionManager = UserSessionManager(this@ProfileActivity)
        setProfileDetails()
        onClickOperation()
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

    }
    @SuppressLint("SetTextI18n")
    private fun setProfileDetails() {
        if (sessionManager.isloggin())
        {
            binding.layerLogin.visibility = View.GONE
            binding.logOut.visibility = View.VISIBLE
            when {
                sessionManager.getName().isNotEmpty() -> {
                    binding.txtUserName.text = "Hi, ${sessionManager.getName()}"
                }
                else -> {
                    binding.txtUserName.text = "Hi, Unknown User"
                }
            }

        }else
        {
            binding.layerLogin.visibility = View.VISIBLE
            binding.logOut.visibility = View.GONE
            binding.txtUserName.text = "Hi, Unknown User"

        }
    }

    private fun onClickOperation() {
        binding.llPersonalInfo.setOnClickListener {
            showPersonalDialog("personal_info")
        }
        binding.rlFollowing.setOnClickListener {
            showPersonalDialog("following")
        }
        binding.rlDeliveryDetail.setOnClickListener {
            showPersonalDialog("delivery_details")
        }
        binding.rlNotificationSetting.setOnClickListener {
            showPersonalDialog("notification_setting")
        }
        binding.logOut.setOnClickListener {
            showLogOutDialog()
        }
        binding.layerLogin.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))

        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun showLogOutDialog() {
        profileEditBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(profileEditBinding.root)
        profileEditBinding.llNotifiPlan.visibility = View.GONE
        profileEditBinding.llLogOut.visibility = View.VISIBLE
        profileEditBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
        profileEditBinding.btnYes.setOnClickListener {
            sessionManager.logoutUser()
            finishAffinity()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            dialog.dismiss()
        }
        profileEditBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPersonalDialog(strValue: String) {
        profileEditBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(profileEditBinding.root)
        when (strValue) {
            "personal_info" -> {
                profileEditBinding.llPersonaData.visibility = View.VISIBLE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
                profileEditBinding.editFullName.setText(sessionManager.getName())
                profileEditBinding.editPhone.setText(sessionManager.getMobileNo())
                profileEditBinding.editEmail.setText(sessionManager.getEmail())
                profileEditBinding.editEmail.isEnabled=false
                profileEditBinding.editDob.isEnabled=false
                if(sessionManager.getDob()!="null")
                {
                    profileEditBinding.editDob.setText(sessionManager.getDob())
                }else
                {
                    profileEditBinding.editDob.setText("DD-MM-YYYY")

                }
                if (getSetting("GENDER","")!!.isNotEmpty())
                profileEditBinding.textGender.text=getSetting("GENDER","").toString()
                else if(sessionManager.getGender().isNotEmpty())
                {
                    profileEditBinding.textGender.text=sessionManager.getGender()
                }else
                {
                    profileEditBinding.textGender.text="Gender"
                }

                // profileEditBinding.editDob.setOnClickListener {}
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    requestProfileUpdate(profileEditBinding.editPhone.text.toString(),profileEditBinding.editFullName.text.toString(),
                        profileEditBinding.editDob.text.toString(),profileEditBinding.textGender.text.toString() )
                    dialog.dismiss()
                }
            }
            "following" -> {
                profileEditBinding.llFollowing.visibility = View.VISIBLE
                profileEditBinding.btnSave.visibility = View.GONE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_following)
                profileEditBinding.recyclerFollowing.apply {
                    layoutManager =
                        LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.VERTICAL, false)
                    adapter = FollowingListAdapter(this@ProfileActivity, courseImg)
                }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
            }
            "delivery_details" -> {
                profileEditBinding.llMap.visibility = View.VISIBLE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_delivery_detail)
                profileEditBinding.btnSave.text = ("Apply")
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    dialog.dismiss()
                }
            }
            "notification_setting" -> {
                profileEditBinding.llNotfySetting.visibility = View.VISIBLE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_notification_setting)
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    dialog.dismiss()
                }
            }
        }


        dialog.show()
    }

    private fun requestProfileUpdate(
        mobile: String,
        name: String,
        dob: String,
        gender: String
    ) {
        if (checkNetwork(this))
        {
            showLoader()
            mViewModel.requestUpdateProfile(mobile, name,dob,gender,sessionManager.getUserToken())
        }else
        {
            showAlertInfo("Please check internet connection",this)
        }

    }

}