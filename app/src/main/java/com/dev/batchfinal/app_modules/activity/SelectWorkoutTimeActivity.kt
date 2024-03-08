package com.dev.batchfinal.app_modules.activity

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivitySelectWorkoutTimeBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectWorkoutTimeActivity : BaseActivity<ActivitySelectWorkoutTimeBinding>() {
  private val viewModel: AllViewModel by viewModels()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.btnNext.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_save_plan_layout = dialog.findViewById<LinearLayout>(R.id.ll_save_plan_layout)
        val layout_notification = dialog.findViewById<LinearLayout>(R.id.layout_notification)
        val btn_allow = dialog.findViewById<Button>(R.id.btn_allow)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_save_plan_layout!!.visibility = View.GONE
        layout_notification!!.visibility = View.VISIBLE
        btn_allow!!.setOnClickListener {
            //code for save week price
            startActivity(Intent(this@SelectWorkoutTimeActivity, MainActivity::class.java))
        }

        dialog.show()
    }

    override fun getViewBinding() = ActivitySelectWorkoutTimeBinding.inflate(layoutInflater)
}