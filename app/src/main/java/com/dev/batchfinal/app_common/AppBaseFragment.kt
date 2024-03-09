package com.dev.batchfinal.app_common

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dev.batchfinal.R

abstract class AppBaseFragment <B : ViewBinding> : Fragment() {
    protected lateinit var binding: B
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        initUI()
        return binding.root
    }

    fun showAlertInfo(applicationMessage: String, context: Context) {
        val builder = AlertDialog.Builder(context)
            .create()
        val view = layoutInflater.inflate(R.layout.alert_info_batch, null)
        val userInfo = view.findViewById<TextView>(R.id.alertMessage)
        val onCLickOkay = view.findViewById<AppCompatTextView>(R.id.txt_okay)
        userInfo.text = applicationMessage
        builder.setView(view)
        onCLickOkay.setOnClickListener {

            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }

    protected fun checkNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network)
            ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    protected fun showProgress(mContext: Context) {
        if (progressDialog == null) {

            progressDialog = ProgressDialog(mContext)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar_Large)
            progressDialog!!.setMessage("loading...")
            progressDialog!!.setCanceledOnTouchOutside(false)
        }
        progressDialog!!.show()
    }

    protected fun dismissProgress() {

        //progressDialog
        progressDialog?.dismiss()
    }

    protected fun showLoader() {
        try {
            LoaderFragment.showLoader(requireActivity().supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun hideLoader() {
        try {
            LoaderFragment.dismissLoader(requireActivity().supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun startRelativeAnimation(mainLayout: RelativeLayout) {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_top)
        mainLayout.startAnimation(aniSlide)
    }

    protected fun startLinearLayoutAnimation(mainLayout: LinearLayout) {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_top)
        mainLayout.startAnimation(aniSlide)
    }

    protected fun showToastShort(toastDescription: String, ctx: Context) {
        Toast.makeText(ctx, toastDescription, Toast.LENGTH_SHORT).show()
    }

    protected fun showToastLong(toastDescription: String, ctx: Context) {
        Toast.makeText(ctx, toastDescription, Toast.LENGTH_LONG).show()
    }

    protected fun setSetting(key: String, value: String) {
        val settings = requireContext().getSharedPreferences("BATCH_FINAL_PREF", 0)
        val editor = settings.edit()
        editor.putString(key, value)

        // Commit the edits!
        editor.commit()
    }

    protected fun getSetting(key: String, def: String): String? {

        val settings = requireContext().getSharedPreferences("BATCH_FINAL_PREF", 0)
        return settings.getString(key, def)

    }



    abstract fun initUI()

    abstract fun getViewBinding(): B

}