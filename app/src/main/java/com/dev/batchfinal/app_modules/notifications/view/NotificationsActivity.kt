package com.dev.batchfinal.app_modules.notifications.view

import android.app.AlertDialog
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dev.batchfinal.R
import com.dev.batchfinal.app_common.AppBaseActivity

import com.dev.batchfinal.app_modules.notifications.network_service.NotificationNetworkService
import com.dev.batchfinal.app_modules.notifications.repository.NotificationRepository
import com.dev.batchfinal.app_modules.notifications.viewmodel.NotificationFactoryModel
import com.dev.batchfinal.app_modules.notifications.viewmodel.NotificationViewModel

import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.databinding.ActivityNotificationsBinding

class NotificationsActivity:AppBaseActivity<ActivityNotificationsBinding>() {
    private lateinit var sessionManager: UserSessionManager


    private lateinit var mViewModel: NotificationViewModel
    private val retrofitService = NotificationNetworkService.create()
    override fun getViewBinding() = ActivityNotificationsBinding.inflate(layoutInflater)

    override fun initUI() {
        sessionManager = UserSessionManager(this@NotificationsActivity)
        onClickOperation()


    }


    override fun onStart() {
        super.onStart()
        mViewModel = ViewModelProvider(
            this,
            NotificationFactoryModel(NotificationRepository(retrofitService))
        )[NotificationViewModel::class.java]
       requestLatestNotification()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getNotifications.observe(this, Observer {
            hideLoader()
            //showAlertInfo("No notification found", this)

            //NOTE- Response model are in object form- require as array object

            val builder = AlertDialog.Builder(this)
                .create()
            val view = layoutInflater.inflate(R.layout.alert_info_batch, null)
            val userInfo = view.findViewById<TextView>(R.id.alertMessage)
            val onCLickOkay = view.findViewById<AppCompatTextView>(R.id.txt_okay)
            userInfo.text = "No notification found"
            builder.setView(view)
            onCLickOkay.setOnClickListener {
               finish()
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(true)
            builder.show()

        })

        mViewModel.errorMessage.observe(this, Observer {
            showAlertInfo(it.toString(), this)
            hideLoader()
        })

    }

    private fun requestLatestNotification() {
        if (checkNetwork(this))
        {
            showLoader()
            mViewModel.requestNotifications(sessionManager.getUserToken())
        }else
        {
            showAlertInfo("Please check internet connection",this)
        }
    }

    private fun onClickOperation() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

}