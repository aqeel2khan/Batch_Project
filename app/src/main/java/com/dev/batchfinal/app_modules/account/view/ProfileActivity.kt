package com.dev.batchfinal.app_modules.account.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
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
    private var courseImg =
        ArrayList(listOf(R.drawable.profile_image, R.drawable.normal_boy, R.drawable.profile_image))

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
        mViewModel.getUpdatedProfile.observe(this, Observer {
            sessionManager.updateUserSession(
                it.data!!.mobile.toString(),
                it.data!!.name.toString(),
                it.data!!.dob.toString(),
                it.data!!.gender.toString()
            )
            binding.txtUserName.text = "Hi, ${sessionManager.getName()}"

            showAlertInfo(it.message.toString(), this)
            hideLoader()
        })
        mViewModel.errorMessage.observe(this, Observer {
            showAlertInfo(it.toString(), this)
            hideLoader()
        })


    }

    @SuppressLint("SetTextI18n")
    private fun setProfileDetails() {
        if (sessionManager.isloggin()) {
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

        } else {
            binding.layerLogin.visibility = View.VISIBLE
            binding.logOut.visibility = View.GONE
            binding.txtUserName.text = "Hi, Unknown User"

        }
    }

    private fun onClickOperation() {
        binding.tvUpdateProfilePic.setOnClickListener {

        }
        binding.llPersonalInfo.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("personal_info")
            } else {
                askUserForLogin("Required authorization to access personal info.", this)
            }
        }
        binding.rlFollowing.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("following")
            } else {
                askUserForLogin("Required authorization to access following.", this)
            }

        }
        binding.rlDeliveryDetail.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("delivery_details")
            } else {
                askUserForLogin("Required authorization to access delivery details.", this)
            }

        }
        binding.rlNotificationSetting.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("notification_setting")
            } else {
                askUserForLogin("Required authorization to access notification settings.", this)
            }

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
               // profileEditBinding.btnSave.visibility=View.VISIBLE
                //profileEditBinding.btnApply.visibility=View.GONE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
                profileEditBinding.editFullName.setText(sessionManager.getName())
                profileEditBinding.editPhone.setText(sessionManager.getMobileNo())
                profileEditBinding.editEmail.setText(sessionManager.getEmail())
                profileEditBinding.editEmail.isEnabled = false
               // profileEditBinding.editDob.isEnabled = false
                if (sessionManager.getDob() != "null") {
                    profileEditBinding.editDob.setText(sessionManager.getDob())
                }
                else {
                    profileEditBinding.editDob.setText("DD-MM-YYYY")

                }
                if (getSetting("GENDER", "")!!.isNotEmpty())
                   // profileEditBinding.textGender.text = getSetting("GENDER", "").toString()
                if(getSetting("GENDER", "").toString()=="Male")
                profileEditBinding.spinnerGender.setSelection(1)
                else if(getSetting("GENDER", "").toString()=="Female")
                {
                    profileEditBinding.spinnerGender.setSelection(2)

                }

                else if (sessionManager.getGender().isNotEmpty()) {
                   // profileEditBinding.textGender.text = sessionManager.getGender()
                    if(sessionManager.getGender()=="Male")
                        profileEditBinding.spinnerGender.setSelection(1)
                    else if(sessionManager.getGender()=="Female")
                    {
                        profileEditBinding.spinnerGender.setSelection(2)

                    }else if(sessionManager.getGender()=="null")
                    {
                        profileEditBinding.spinnerGender.setSelection(0)

                    }
                } else {
                   // profileEditBinding.textGender.text = "Gender"
                    profileEditBinding.spinnerGender.setSelection(0)

                }

                profileEditBinding.editDob.setOnClickListener {
                     showDatePickerDialog(profileEditBinding.editDob,this@ProfileActivity)
                 }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    if (checkNetwork(this@ProfileActivity))
                    {
                        if (profileEditBinding.editFullName.text.toString().isEmpty())
                        {
                            showAlertInfo("Please enter name.",this@ProfileActivity)
                        }else if(profileEditBinding.editPhone.text.toString().isEmpty())
                        {
                            showAlertInfo("Please enter mobile number.",this@ProfileActivity)

                        }else if(profileEditBinding.editPhone.text!!.length<10)
                        {
                            showAlertInfo("Please enter correct mobile number.",this@ProfileActivity)

                        }else if(profileEditBinding.spinnerGender.selectedItem.toString()=="Select Gender")
                        {
                            showAlertInfo("Please select gender.",this@ProfileActivity)

                        }else
                        {
                            //                                profileEditBinding.textGender.text.toString()
                            requestProfileUpdate(
                                profileEditBinding.editPhone.text.toString(),
                                profileEditBinding.editFullName.text.toString(),
                                profileEditBinding.editDob.text.toString(),
                                profileEditBinding.spinnerGender.selectedItem.toString()
                            )
                            dialog.dismiss()


                        }

                    }else
                    {
                        showAlertInfo("Please check internet connection.",this@ProfileActivity)
                    }
                }
            }

            "following" -> {
                profileEditBinding.llFollowing.visibility = View.VISIBLE
                profileEditBinding.btnSave.visibility = View.GONE
               // profileEditBinding.btnApply.visibility=View.GONE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_following)
                profileEditBinding.recyclerFollowing.apply {
                    layoutManager =
                        LinearLayoutManager(
                            this@ProfileActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
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
                //profileEditBinding.btnSave.visibility=View.GONE
               // profileEditBinding.btnApply.visibility=View.VISIBLE
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    dialog.dismiss()
                }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
            }

            "notification_setting" -> {
                profileEditBinding.llNotfySetting.visibility = View.VISIBLE
                profileEditBinding.txtTitle.text =
                    resources.getString(R.string.txt_notification_setting)
                profileEditBinding.btnSave.visibility=View.VISIBLE
                //profileEditBinding.btnApply.visibility=View.GONE
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    dialog.dismiss()
                }
                profileEditBinding.closePersonalInfo.setOnClickListener {
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
        if (checkNetwork(this)) {
            showLoader()
            mViewModel.requestUpdateProfile(
                mobile,
                name,
                dob,
                gender,
                sessionManager.getUserToken()
            )
        } else {
            showAlertInfo("Please check internet connection", this)
        }

    }

}