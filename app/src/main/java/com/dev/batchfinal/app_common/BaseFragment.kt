package com.dev.batchfinal.app_common

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.app_utils.AppSharedPreferences
import com.dev.batchfinal.viewmodel.BaseViewModel
import javax.inject.Inject


@Suppress("DEPRECATION")
abstract class BaseFragment<B : ViewBinding> : Fragment() {
    @Inject
    protected lateinit var sharedPreferences: AppSharedPreferences
    protected lateinit var binding: B
    private var spinningDialog: Dialog? = null
    private var progressDialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        initializeCommonObservers(getViewModel())
        initUi()
        return binding.root
    }

    fun showLoader() {
        try {
            LoaderFragment.showLoader(requireActivity().supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoader() {
        try {
            LoaderFragment.dismissLoader(requireActivity().supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showProgressDialog(mContext: Context) {
        if (progressDialog==null) {
 progressDialog = ProgressDialog(mContext)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Progress Dialog Style Spinner
        progressDialog!!.setIndeterminateDrawable(mContext.resources.getDrawable(
            R.drawable.loading_anim, null
        )
        )
        progressDialog!!.setMessage(mContext.resources.getString(R.string.loading))
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
        }

    }

    fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    protected open fun setErrorAndSpinnerObserver(): Boolean {
        return true
    }
    private fun initializeCommonObservers(viewModel: BaseViewModel) {
        if (setErrorAndSpinnerObserver()) {
            viewModel.error.observe(requireActivity(), { message ->
                message?.let(::showError)
            })

            viewModel.spinner.observe(requireActivity(), { loading ->
                if (loading) {
//                    showLoader()
                } else {
//                    hideLoader()
                }
            })
        }
    }
    protected fun handleHeader(isVisible: Boolean = true, perform: () -> Unit = {}){
        (requireActivity() as? MainActivity)?.handleHeader(isVisible, perform)
    }

    protected fun handleBottomBar(isVisible: Boolean = true, perform: () -> Unit = {}){
        (requireActivity() as? MainActivity)?.handleBottomBar(isVisible, perform)
    }

    protected fun handleTitle(headerTitle: String, perform: () -> Unit = {}){
        (requireActivity() as? MainActivity)?.handleTitle(headerTitle, perform)
    }

    abstract fun getViewModel(): BaseViewModel
    abstract fun initUi()
    abstract fun getViewBinding(): B
}