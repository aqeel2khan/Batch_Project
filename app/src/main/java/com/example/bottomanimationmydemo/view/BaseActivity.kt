package com.example.bottomanimationmydemo.view

import android.app.Dialog
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.network.NetworkConnectionLiveData
import com.example.bottomanimationmydemo.utils.AppSharedPreferences
import com.example.bottomanimationmydemo.utils.LoaderFragment
import com.example.bottomanimationmydemo.view.fragments.NetworkConnectivityFragment
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
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
    abstract fun getViewModel(): BaseViewModel
    abstract fun initUi()
    abstract fun getViewBinding(): B
}