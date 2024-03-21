package com.dev.batchfinal.app_utils

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.account.helper.URIPathHelper
import com.dev.batchfinal.app_modules.account.minterface.ImageResizeCallback
import com.dev.batchfinal.app_modules.account.view.ProfileActivity
import com.dev.batchfinal.app_utils.MyConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import id.zelory.compressor.Compressor
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File

class CommonUtils private constructor() {

    companion object {
        private var progressDialog: ProgressDialog? = null
        private var compressedFile: File? = null
        private var encodedBase64 = ""
        private lateinit var mListenerImageResizeCallback: ImageResizeCallback

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

            if (progressDialog == null) {

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



        }

        fun dismissDialog() {
            progressDialog?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
                progressDialog = null // Reset the reference
            }
        }

        fun getResizedImageBase64String(
            activity: Activity?,
            imgFile: File?,
            mListener: ImageResizeCallback,
            ctx: Context
        ) {
            try {
                encodedBase64 = ""
                mListenerImageResizeCallback = mListener

                Compressor.getDefault(activity)
                    .compressToFileAsObservable(imgFile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        compressedFile = it.absoluteFile
                        val myBit = BitmapFactory.decodeFile(compressedFile!!.absolutePath)
                        val streams = ByteArrayOutputStream()
                    val scaleBitmap=    myBit.compress(Bitmap.CompressFormat.PNG, 100, streams)
                        val mCompressbyteArray = streams.toByteArray()
                        encodedBase64 = Base64.encodeToString(mCompressbyteArray, Base64.DEFAULT)

                        val addressImageUri: Uri = URIPathHelper().getImageUri(ctx, myBit)
                       val profileImgPath= URIPathHelper().getPath(ctx,addressImageUri).toString()
                        //returning base64
                      //  mListenerImageResizeCallback.onSuccess(encodedBase64, myBit, compressedFile)
                        //returning path
                        mListenerImageResizeCallback.onSuccess(profileImgPath, myBit, compressedFile)

                    }
                    ) {
                        mListenerImageResizeCallback.onFailure(it.message)
                    }
            } catch (e: java.lang.Exception) {
                mListenerImageResizeCallback.onFailure(e.message)
                e.printStackTrace()
            }
        }


        fun checkPermissions(context: Context?): Boolean {
            val currentAPIVersion = Build.VERSION.SDK_INT
            return if (currentAPIVersion >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context, Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context, Manifest.permission.READ_MEDIA_VIDEO
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.CAMERA
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.READ_MEDIA_VIDEO
                        )
                    ) {
                        ActivityCompat.requestPermissions(
                            (context as Activity?)!!,
                            arrayOf<String>(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.POST_NOTIFICATIONS,
                                Manifest.permission.READ_MEDIA_VIDEO
                            ),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    } else {
                        ActivityCompat.requestPermissions(
                            (context as Activity?)!!,
                            arrayOf<String>(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.POST_NOTIFICATIONS,
                                Manifest.permission.READ_MEDIA_VIDEO
                            ),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                    false
                } else {
                    true
                }
            } else if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context, Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.CAMERA
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as Activity?)!!,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        ActivityCompat.requestPermissions(
                            (context as Activity?)!!,
                            arrayOf<String>(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    } else {
                        ActivityCompat.requestPermissions(
                            (context as Activity?)!!,
                            arrayOf<String>(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                    false
                } else {
                    true
                }
            } else {
                true
            }
        }

    }
}