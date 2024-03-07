package com.dev.batchfinal.view.account

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.FollowingListAdapter
import com.dev.batchfinal.databinding.ActivityProfileBinding
import com.dev.batchfinal.databinding.ProfileEditDialogBinding
import com.dev.batchfinal.session.UserSessionManager
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
    private val viewModel: AllViewModel by viewModels()
    lateinit var dialogBinding: ProfileEditDialogBinding
    private lateinit var sessionManager: UserSessionManager

    var courseImg = ArrayList(
        Arrays.asList(
            R.drawable.profile_image, R.drawable.normal_boy, R.drawable.profile_image
        )
    )

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        sessionManager = UserSessionManager(this@ProfileActivity)
        setProfileDetails()
        buttonClicks()
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

    private fun buttonClicks() {
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
        dialogBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.llNotifiPlan.visibility = View.GONE
        dialogBinding.llLogOut.visibility = View.VISIBLE
        dialogBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
        dialogBinding.btnYes.setOnClickListener {
            sessionManager.logoutUser()
            finishAffinity()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            dialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPersonalDialog(strValue: String) {
        dialogBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        if (strValue.equals("personal_info")) {
            dialogBinding.llPersonaData.visibility = View.VISIBLE
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
            dialogBinding.btnSave.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        } else if (strValue.equals("following")) {
            dialogBinding.llFollowing.visibility = View.VISIBLE
            dialogBinding.btnSave.visibility = View.GONE
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_following)
            dialogBinding.recyclerFollowing.apply {
                layoutManager =
                    LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.VERTICAL, false)
                    adapter = FollowingListAdapter(this@ProfileActivity, courseImg)
            }
            dialogBinding.imgDown.setOnClickListener {
                dialog.dismiss()
            }
        } else if (strValue.equals("delivery_details")) {
            dialogBinding.llMap.visibility = View.VISIBLE
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_delivery_detail)
            dialogBinding.btnSave.text = ("Apply")
            dialogBinding.btnSave.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        } else if (strValue.equals("notification_setting")) {
            dialogBinding.llNotfySetting.visibility = View.VISIBLE
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_notification_setting)
            dialogBinding.btnSave.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        }


        dialog.show()
    }

    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
}