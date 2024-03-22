package com.dev.batchfinal.app_modules.account.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.BuildConfig
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.FollowingListAdapter
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_common.ImagePickerActivity
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_ASPECT_RATIO_X
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_ASPECT_RATIO_Y
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO
import com.dev.batchfinal.app_common.ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT
import com.dev.batchfinal.app_common.ImagePickerActivity.REQUEST_IMAGE_CAPTURE
import com.dev.batchfinal.app_modules.account.minterface.ImageResizeCallback
import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_modules.account.viewmodel.AccountFactoryModel
import com.dev.batchfinal.app_modules.account.viewmodel.AccountViewModel
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.CommonUtils.Companion.checkPermissions
import com.dev.batchfinal.app_utils.CommonUtils.Companion.getResizedImageBase64String
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.databinding.ActivityMainBinding
import com.dev.batchfinal.databinding.ActivityProfileBinding
import com.dev.batchfinal.databinding.ProfileEditDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class ProfileActivity : AppBaseActivity<ActivityProfileBinding>(),ImageResizeCallback {
     private val REQUEST_IMAGE = 100
    private val SAMPLE_CROPPED_IMAGE_NAME = "BatchImage"
    private val requestMode=1
    private val resumeMode = 2
    private var userChoosenTask = ""
    private lateinit var mListener: ImageResizeCallback

    private lateinit var mViewModel: AccountViewModel
    private val retrofitService = AccountNetworkService.create()
    private lateinit var profileEditBinding: ProfileEditDialogBinding
    private lateinit var actMainBinding: ActivityMainBinding

    private lateinit var sessionManager: UserSessionManager
    private var courseImg =
        ArrayList(listOf(R.drawable.profile_image, R.drawable.normal_boy, R.drawable.profile_image))

    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)

    override fun initUI() {
        sessionManager = UserSessionManager(this@ProfileActivity)

        if (sessionManager.getProfileImgPath()!="null")
        {
            actMainBinding = ActivityMainBinding.inflate(layoutInflater)
            MyUtils.loadImage(
                actMainBinding.imProfile, MyConstant.IMAGE_BASE_URL+sessionManager.getProfileImgPath()
            )
            MyUtils.loadImage(
                binding.ivProfile, MyConstant.IMAGE_BASE_URL+sessionManager.getProfileImgPath()
            )

        }
        mListener=this
        setProfileDetails()
        onClickOperation()
    }

    override fun onStart() {
        super.onStart()
        mViewModel = ViewModelProvider(
            this,
            AccountFactoryModel(AccountRepository(retrofitService))
        )[AccountViewModel::class.java]

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        mViewModel.getUpdatedProfile.observe(this) {
            sessionManager.updateUserSession(
                it.data!!.mobile.toString(),
                it.data!!.name.toString(),
                it.data!!.dob.toString(),
                it.data!!.gender.toString(),
                it.data!!.profilePhotoPath.toString()
            )
            binding.txtUserName.text = "Hi, ${sessionManager.getName()}"

            showAlertInfo(it.message.toString(), this)
            hideLoader()
        }
        mViewModel.getUpdatedProfileImg.observe(this) {
            if (it.status == true) {
                sessionManager.updateUserSession(
                    it.data.mobile.toString(),
                    it.data.name.toString(),
                    it.data.dob.toString(),
                    it.data.gender.toString(),
                    it.data.profilePhotoPath.toString()
                )
                binding.txtUserName.text = "Hi, ${sessionManager.getName()}"
                MyUtils.loadImage(
                    binding.ivProfile,
                    MyConstant.IMAGE_BASE_URL +sessionManager.getProfileImgPath()
                )
                showAlertInfo(it.message.toString(), this)
                it.status = false

            }

            hideLoader()
        }

        mViewModel.errorMessage.observe(this, Observer {
            showAlertInfo(it.toString(), this)
            hideLoader()
        })


    }

    @SuppressLint("SetTextI18n")
    private fun setProfileDetails() {
        if (sessionManager.isloggin()) {
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

        } else {
            binding.layerLogin.visibility = View.VISIBLE
            binding.logOut.visibility = View.GONE
            binding.txtUserName.text = "Hi, Unknown User"

        }
    }

    private fun onClickOperation() {
        binding.tvUpdateProfilePic.setOnClickListener {

        openOtionDialog()

        }
        binding.llPersonalInfo.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("personal_info")
            } else {
                askUserForLogin("Required authorization to access personal info.", this)
            }
        }
        binding.rlFollowing.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("following")
            } else {
                askUserForLogin("Required authorization to access following.", this)
            }

        }
        binding.rlDeliveryDetail.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("delivery_details")
            } else {
                askUserForLogin("Required authorization to access delivery details.", this)
            }

        }
        binding.rlNotificationSetting.setOnClickListener {
            if (sessionManager.isloggin()) {
                showPersonalDialog("notification_setting")
            } else {
                askUserForLogin("Required authorization to access notification settings.", this)
            }

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

    private fun openOtionDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.row_select_camera_gallery)
        val btnCamera = bottomSheetDialog.findViewById<Button>(R.id.btn_camera)
        val btnGallery = bottomSheetDialog.findViewById<Button>(R.id.btn_gallery)

        btnCamera!!.setOnClickListener {
            userChoosenTask = "CAMERA"
            val result: Boolean = checkPermissions(this@ProfileActivity)
            if (result) {
                launchCameraIntent()
                //dispatchTakePictureIntent();
                bottomSheetDialog.dismiss()
            } else {
                bottomSheetDialog.dismiss()
            }
        }
        btnGallery!!.setOnClickListener {
            userChoosenTask = "GALLERY"
            val result: Boolean = checkPermissions(this@ProfileActivity)
            if (result) {
                //galleryIntent();
                //launchGalleryIntent();
                pickFromGallery()
                bottomSheetDialog.dismiss()
            } else {
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.show()


    }
    private fun launchCameraIntent() {
        val intent = Intent(
            this@ProfileActivity,
            ImagePickerActivity::class.java
        )
        intent.putExtra(INTENT_IMAGE_PICKER_OPTION, REQUEST_IMAGE_CAPTURE)
        // setting aspect ratio
        intent.putExtra(INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(INTENT_ASPECT_RATIO_Y, 1)
        // setting maximum bitmap width and height
        intent.putExtra(INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }
    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .setType("image/*")
            .addCategory(Intent.CATEGORY_OPENABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestMode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestMode) {
                val selectedUri = data!!.data
                if (selectedUri != null) {
                    startCrop(selectedUri)
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Cannot retrieve selected image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                val resultUri = UCrop.getOutput(data!!)
                if (resultUri != null) {
                    onCaptureImageResult(resultUri)
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Cannot retrieve selected image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (requestCode == REQUEST_IMAGE) {
                val uri = data!!.getParcelableExtra<Uri>("path")
                uri?.let { onCaptureImageResult(it) }
            }

        }

    }

    private fun startCrop(uri: Uri) {
        var destinationFileName: String = SAMPLE_CROPPED_IMAGE_NAME
        destinationFileName += ".png"
        var uCrop = UCrop.of(uri, Uri.fromFile(File(cacheDir, destinationFileName)))
        uCrop = basisConfig(uCrop)
        uCrop = advancedConfig(uCrop)

        // else start uCrop Activity
        uCrop.start(this@ProfileActivity)
    }
    private fun basisConfig(uCrop: UCrop): UCrop? {
        return uCrop
    }
    private fun advancedConfig(uCrop: UCrop): UCrop? {
        val options = UCrop.Options()
        options.setCompressionFormat(Bitmap.CompressFormat.PNG)
        options.setCompressionQuality(100)
        options.setToolbarColor(ContextCompat.getColor(this, R.color.header_color))
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.header_color))
        options.setActiveControlsWidgetColor(ContextCompat.getColor(this, R.color.white))
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.white))
        options.setRootViewBackgroundColor(ContextCompat.getColor(this, R.color.white))
        options.setHideBottomControls(true)
        options.setFreeStyleCropEnabled(false)
        //options.withAspectRatio(520f, 130f) //Mobile

         //options.withAspectRatio(425f,106f);//Mobile
        options.withAspectRatio(1f,1f);//Mobile

        return uCrop.withOptions(options)
    }

    private fun onCaptureImageResult(uri: Uri) {
        try {
            getResizedImageBase64String(this@ProfileActivity, File(uri.path), mListener,this@ProfileActivity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showLogOutDialog() {
        profileEditBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(profileEditBinding.root)
        profileEditBinding.llNotifiPlan.visibility = View.GONE
        profileEditBinding.llLogOut.visibility = View.VISIBLE
        profileEditBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
        profileEditBinding.btnYes.setOnClickListener {
            sessionManager.logoutUser()
            finishAffinity()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            dialog.dismiss()
        }
        profileEditBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPersonalDialog(strValue: String) {
        profileEditBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(profileEditBinding.root)
        when (strValue) {
            "personal_info" -> {
                profileEditBinding.llPersonaData.visibility = View.VISIBLE
               // profileEditBinding.btnSave.visibility=View.VISIBLE
                //profileEditBinding.btnApply.visibility=View.GONE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_personal_info)
                profileEditBinding.editFullName.setText(sessionManager.getName())
                profileEditBinding.editPhone.setText(sessionManager.getMobileNo())
                profileEditBinding.editEmail.setText(sessionManager.getEmail())
                profileEditBinding.editEmail.isEnabled = false
               // profileEditBinding.editDob.isEnabled = false
                if (sessionManager.getDob() != "null") {
                    profileEditBinding.editDob.setText(sessionManager.getDob())
                }
                else {
                    profileEditBinding.editDob.setText("DD-MM-YYYY")

                }
                if (getSetting("GENDER", "")!!.isNotEmpty())
                   // profileEditBinding.textGender.text = getSetting("GENDER", "").toString()
                if(getSetting("GENDER", "").toString()=="Male")
                profileEditBinding.spinnerGender.setSelection(1)
                else if(getSetting("GENDER", "").toString()=="Female")
                {
                    profileEditBinding.spinnerGender.setSelection(2)

                }

                else if (sessionManager.getGender().isNotEmpty()) {
                   // profileEditBinding.textGender.text = sessionManager.getGender()
                    if(sessionManager.getGender()=="Male")
                        profileEditBinding.spinnerGender.setSelection(1)
                    else if(sessionManager.getGender()=="Female")
                    {
                        profileEditBinding.spinnerGender.setSelection(2)

                    }else if(sessionManager.getGender()=="null")
                    {
                        profileEditBinding.spinnerGender.setSelection(0)

                    }
                } else {
                   // profileEditBinding.textGender.text = "Gender"
                    profileEditBinding.spinnerGender.setSelection(0)

                }

                profileEditBinding.editDob.setOnClickListener {
                     showDatePickerDialog(profileEditBinding.editDob,this@ProfileActivity)
                 }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    if (checkNetwork(this@ProfileActivity))
                    {
                        if (profileEditBinding.editFullName.text.toString().isEmpty())
                        {
                            showAlertInfo("Please enter name.",this@ProfileActivity)
                        }else if(profileEditBinding.editPhone.text.toString().isEmpty())
                        {
                            showAlertInfo("Please enter mobile number.",this@ProfileActivity)

                        }else if(profileEditBinding.editPhone.text!!.length<10)
                        {
                            showAlertInfo("Please enter correct mobile number.",this@ProfileActivity)

                        }else if(profileEditBinding.spinnerGender.selectedItem.toString()=="Select Gender")
                        {
                            showAlertInfo("Please select gender.",this@ProfileActivity)

                        }else
                        {
                            //                                profileEditBinding.textGender.text.toString()
                            requestProfileUpdate(
                                profileEditBinding.editPhone.text.toString(),
                                profileEditBinding.editFullName.text.toString(),
                                profileEditBinding.editDob.text.toString(),
                                profileEditBinding.spinnerGender.selectedItem.toString()
                            )
                            dialog.dismiss()


                        }

                    }else
                    {
                        showAlertInfo("Please check internet connection.",this@ProfileActivity)
                    }
                }
            }

            "following" -> {
                profileEditBinding.llFollowing.visibility = View.VISIBLE
                profileEditBinding.btnSave.visibility = View.GONE
               // profileEditBinding.btnApply.visibility=View.GONE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_following)
                profileEditBinding.recyclerFollowing.apply {
                    layoutManager =
                        LinearLayoutManager(
                            this@ProfileActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    adapter = FollowingListAdapter(this@ProfileActivity, courseImg)
                }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
            }

            "delivery_details" -> {
                profileEditBinding.llMap.visibility = View.VISIBLE
                profileEditBinding.txtTitle.text = resources.getString(R.string.txt_delivery_detail)
                profileEditBinding.btnSave.text = ("Apply")
                //profileEditBinding.btnSave.visibility=View.GONE
               // profileEditBinding.btnApply.visibility=View.VISIBLE
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    dialog.dismiss()
                }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }
            }

            "notification_setting" -> {
                profileEditBinding.llNotfySetting.visibility = View.VISIBLE
                profileEditBinding.txtTitle.text =
                    resources.getString(R.string.txt_notification_setting)
                profileEditBinding.btnSave.visibility=View.VISIBLE
                //profileEditBinding.btnApply.visibility=View.GONE
                profileEditBinding.btnSave.setOnClickListener {
                    //code for save week price
                    dialog.dismiss()
                }
                profileEditBinding.closePersonalInfo.setOnClickListener {
                    dialog.dismiss()
                }

            }
        }


        dialog.show()
    }

    private fun requestProfileUpdate(
        mobile: String,
        name: String,
        dob: String,
        gender: String
    ) {
        if (checkNetwork(this)) {
            showLoader()
            mViewModel.requestUpdateProfile(
                mobile,
                name,
                dob,
                gender,
                sessionManager.getUserToken()
            )
        } else {
            showAlertInfo("Please check internet connection", this)
        }

    }

    override fun onSuccess(imagePath: String?, bitmap: Bitmap?, file: File?) {
        //upload data
        Log.e("TO UPLOAD",imagePath.toString())
        mViewModel.requestProfileImgUpdate(imagePath.toString(),sessionManager.getUserToken())
    }

    override fun onFailure(msg: String?) {
        Log.e("FAILURE",msg.toString())

    }

}