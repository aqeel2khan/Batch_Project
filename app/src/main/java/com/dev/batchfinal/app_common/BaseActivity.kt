package com.dev.batchfinal.app_common

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.provider.Settings
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.dev.batchfinal.R
import com.dev.batchfinal.network.NetworkConnectionLiveData
import com.dev.batchfinal.app_utils.AppSharedPreferences
import com.dev.batchfinal.app_modules.fragments.NetworkConnectivityFragment
import com.dev.batchfinal.viewmodel.BaseViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

abstract  class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    @Inject
    protected lateinit var sharedPreferences: AppSharedPreferences
    private var spinningDialog: Dialog? = null
    protected lateinit var binding: B

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initializeCommonObservers(getViewModel())
        initUi()
    }

    fun checkNetwork():Boolean{
        var isAvailable = false
        NetworkConnectionLiveData(this).observe(this, Observer { isConnected ->
            if (isConnected){
                isAvailable = isConnected
                NetworkConnectivityFragment.dismissLoader(supportFragmentManager)
            }else{
                isAvailable = isConnected
                NetworkConnectivityFragment.showLoader(supportFragmentManager)
            }
        })
        return isAvailable
    }


    private fun initializeCommonObservers(viewModel: BaseViewModel) {
        if (setErrorAndSpinnerObserver()) {
            viewModel.error.observe(this) { message ->
                message?.let(::showError)
            }
            viewModel.spinner.observe(this) { loading ->
                if (loading) {
//                    showLoader()
                } else {
//                    hideLoader()
                }
            }
        }
    }
    private fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun startRelativeAnimation(mainLayout: RelativeLayout){
        val aniSlide: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        mainLayout.startAnimation(aniSlide)
    }
    fun startLinearLayoutAnimation(mainLayout: LinearLayout){
        val aniSlide: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        mainLayout.startAnimation(aniSlide)
    }

    fun showLoader() {
        try {
            LoaderFragment.showLoader(supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoader() {
        try {
            LoaderFragment.dismissLoader(supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected open fun setErrorAndSpinnerObserver(): Boolean {
        return true
    }
    fun setSetting(key: String, value: String) {
        val settings = getSharedPreferences("BATCH_FINAL_PREF", 0)
        val editor = settings.edit()
        editor.putString(key, value)

        // Commit the edits!
        editor.commit()
    }

    fun getSetting(key: String, def: String): String? {

        val settings = getSharedPreferences("BATCH_FINAL_PREF", 0)
        return settings.getString(key, def)

    }
    @SuppressLint("HardwareIds")
    fun getDeviceIds(): String? {
        return Settings.Secure.getString(
            getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
    }
    abstract fun getViewModel(): BaseViewModel
    abstract fun initUi()
    abstract fun getViewBinding(): B
}