package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.FollowingListAdapter
import com.example.bottomanimationmydemo.databinding.ActivityProfileBinding
import com.example.bottomanimationmydemo.databinding.ProfileEditDialogBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
   private val viewModel: AllViewModel by viewModels()
   lateinit var dialogBinding: ProfileEditDialogBinding
   var courseImg = ArrayList(Arrays.asList(
      R.drawable.profile_image, R.drawable.normal_boy,R.drawable.profile_image)
   )
   override fun getViewModel(): BaseViewModel {
      return viewModel
   }

   override fun initUi() {
      buttonClicks()
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
   }

   private fun showLogOutDialog() {
      dialogBinding = ProfileEditDialogBinding.inflate(layoutInflater)
      val dialog = BottomSheetDialog(this)
      dialog.setContentView(dialogBinding.root)
      dialogBinding.llNotifiPlan.visibility = View.GONE
      dialogBinding.llLogOut.visibility = View.VISIBLE
      dialogBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
      dialogBinding.btnYes.setOnClickListener {
         //code for save week price
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

      if (strValue.equals("personal_info")){
         dialogBinding.llPersonaData.visibility = View.VISIBLE
         dialogBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
         dialogBinding.btnSave.setOnClickListener {
            //code for save week price
            dialog.dismiss()
         }
      }else if(strValue.equals("following")){
         dialogBinding.llFollowing.visibility = View.VISIBLE
         dialogBinding.btnSave.visibility = View.GONE
         dialogBinding.txtTitle.text = resources.getString(R.string.txt_following)
         dialogBinding.recyclerFollowing.apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.VERTICAL, false)
            adapter = FollowingListAdapter(this@ProfileActivity, courseImg/*, object : PositionItemClickListener<Int>{
               override fun onPositionItemSelected(item: String, postions: Int) {
                  TODO("Not yet implemented")
               }}*/
            )
         }
         dialogBinding.imgDown.setOnClickListener {
            dialog.dismiss()
         }
      }else if (strValue.equals("delivery_details")){
         dialogBinding.llMap.visibility = View.VISIBLE
         dialogBinding.txtTitle.text = resources.getString(R.string.txt_delivery_detail)
         dialogBinding.btnSave.text = ("Apply")
         dialogBinding.btnSave.setOnClickListener {
            //code for save week price
            dialog.dismiss()
         }
      }else if (strValue.equals("notification_setting")){
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