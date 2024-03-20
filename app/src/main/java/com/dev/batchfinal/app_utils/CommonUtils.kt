package com.dev.batchfinal.app_utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import com.dev.batchfinal.R

class CommonUtils private constructor() {

    companion object {
        var progressDialog: ProgressDialog? = null

        /*
                fun showProgressDialog(message: String?,mActivity: Activity) {
                    if (progressDialog == null) {
                        progressDialog = ProgressDialog(mActivity)
                        progressDialog!!.setCancelable(false)
                    }
                    progressDialog!!.setMessage(message)
                    progressDialog!!.show()
                }
        */
        fun progressDialog(mContext: Context) {
            /*  if (progressDialog == null) {

              }*/
            try {
                progressDialog = ProgressDialog(mContext)
                progressDialog!!.isIndeterminate = true
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Progress Dialog Style Spinner
                progressDialog!!.setIndeterminateDrawable(
                    mContext.resources.getDrawable(
                        R.drawable.loading_anim, null
                    )
                )
                progressDialog!!.setMessage(mContext.resources.getString(R.string.loading))
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.show()
            }catch (e:Exception){e.printStackTrace()}

        }

        fun dismissDialog() {
            progressDialog?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
                progressDialog = null // Reset the reference
            }
        }
    }
}