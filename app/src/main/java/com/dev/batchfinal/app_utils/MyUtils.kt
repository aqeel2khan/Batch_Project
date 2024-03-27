package com.dev.batchfinal.app_utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.OpenableColumns
import android.text.InputType
import android.util.Log
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityLoginBinding
import com.dev.batchfinal.databinding.ActivityRegistrationBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

object MyUtils {

    private var passwordNotVisible = 0
    private var newPasswordNotVisible = 0
    private var confirmPasswordNotVisible = 0
    private val showPassword = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    private val hidePassword = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    private val showEyeImage = R.drawable.visible_eye
    private val hideEyeImage = R.drawable.invisible_eye

    fun isValidEmail(email: String?): Boolean {
        var isValid = false
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    // Function to validate mobile number length
    fun isMobileNumberValid(phoneNumber: String): Boolean {
        val minLength = 9
        val maxLength = 13

        // Remove non-digit characters (e.g., spaces, dashes)
        val cleanNumber = phoneNumber.replace("\\D".toRegex(), "")

        // Check if the cleaned number falls within the length range
        return cleanNumber.length in minLength..maxLength
    }

    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri): File? {
        val destinationFilename =
            File(context.filesDir.path + File.separatorChar + queryName(context, uri))
        try {
            context.contentResolver.openInputStream(uri).use { ins ->
                createFileFromStream(ins!!, destinationFilename)
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
        return destinationFilename
    }

    fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
    }

    fun setLocale(lang: String?, context: Context) {
        val locale = Locale(lang!!)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun queryName(context: Context, uri: Uri): String {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    @SuppressLint("CheckResult")
    fun loadBackgroundImage(view: ImageView, url: String?) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.exercise_image)  // Optional: Set a placeholder image while loading
            .error(R.drawable.exercise_image)  // Optional: Set an error image if loading fails
            .into(view)
        //Generating Exception

//Commented@BBh
        /*
                Glide.with(view.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.exercise_image)
                    .into(view)
        */
//Commented@- BBh
        /*
                Glide.with(view.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.exercise_image)
                    .listener(object : RequestListener<Drawable?>
                    {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                        override fun onResourceReady(
                            resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    }
                    )
                    .into(view)
        */
    }

    fun loadImage(view: ImageView, url: String?) {
        try {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.exercise_image)  // Optional: Set a placeholder image while loading
                .error(R.drawable.exercise_image)  // Optional: Set an error image if loading fails
                .into(view)
        } catch (e: Exception) {
           e.printStackTrace()
        }

        //Generating Exception
        /*
                Glide.with(view.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.exercise_image)
                    .into(view)
        */
//Commented@ BBh

        /*
                Glide.with(view.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .apply(RequestOptions.circleCropTransform())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.avtar)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                        override fun onResourceReady(
                            resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
                    .into(view)
        */
    }

    fun passwordShowHide(binding: ViewBinding, s: String) {
        if (binding is ActivityLoginBinding) {
            if (s == MyConstant.newPassword) {
                if (passwordNotVisible == 0) {
                    binding.password.inputType = showPassword
                    binding.passHideShow.setImageResource(showEyeImage)
                    passwordNotVisible = 1
                } else {
                    binding.password.inputType = hidePassword
                    binding.passHideShow.setImageResource(hideEyeImage)
                    passwordNotVisible = 0
                }
            }
        }
        if (binding is ActivityRegistrationBinding) {
            if (s == MyConstant.newPassword) {
                if (passwordNotVisible == 0) {
                    binding.password.inputType = showPassword
                    binding.passHideShow.setImageResource(showEyeImage)
                    passwordNotVisible = 1
                } else {
                    binding.password.inputType = hidePassword
                    binding.passHideShow.setImageResource(hideEyeImage)
                    passwordNotVisible = 0
                }
            } /*else if (s == MyConstant.rePassword) {
                if (newPasswordNotVisible == 0) {
                    binding.rePassword.inputType = showPassword
                    binding.rePassHideShow.setImageResource(showEyeImage)
                    newPasswordNotVisible = 1
                } else {
                    binding.rePassword.inputType = hidePassword
                    binding.rePassHideShow.setImageResource(hideEyeImage)
                    newPasswordNotVisible = 0
                }
            }*/
        }
        /* else if (binding is ActivityChangePasswordBinding){
             if (s == MyConstant.oldPassword) {
                 if (passwordNotVisible == 0) {
                     binding.oldPassword.inputType = showPassword
                     binding.oldPassHideShow.setImageResource(showEyeImage)
                     passwordNotVisible = 1
                 } else {
                     binding.oldPassword.inputType = hidePassword
                     binding.oldPassHideShow.setImageResource(hideEyeImage)
                     passwordNotVisible = 0
                 }
             }else if (s == MyConstant.newPassword) {
                 if (newPasswordNotVisible == 0) {
                     binding.newPassword.inputType = showPassword
                     binding.newPassHideShow.setImageResource(showEyeImage)
                     newPasswordNotVisible = 1
                 } else {
                     binding.newPassword.inputType = hidePassword
                     binding.newPassHideShow.setImageResource(hideEyeImage)
                     newPasswordNotVisible = 0
                 }
             }
             else if (s == MyConstant.confirmNewPassword) {
                 if (confirmPasswordNotVisible == 0) {
                     binding.confirmPassword.inputType = showPassword
                     binding.confirmPassHideShow.setImageResource(showEyeImage)
                     confirmPasswordNotVisible = 1
                 } else {
                     binding.confirmPassword.inputType = hidePassword
                     binding.confirmPassHideShow.setImageResource(hideEyeImage)
                     confirmPasswordNotVisible = 0
                 }
             }
         }*/

//        else if (binding is ActivityUpdatePasswordBinding){
//            if (s == MyConstant.newPassword) {
//                if (passwordNotVisible == 0) {
//                    binding.newPassword.inputType = showPassword
//                    binding.newPasswordHideShow.setImageResource(showEyeImage)
//                    passwordNotVisible = 1
//                } else {
//                    binding.newPassword.inputType = hidePassword
//                    binding.newPasswordHideShow.setImageResource(hideEyeImage)
//                    passwordNotVisible = 0
//                }
//            } else if (s == MyConstant.confirmNewPassword) {
//                if (newPasswordNotVisible == 0) {
//                    binding.confirmPassword.inputType = showPassword
//                    binding.confirmPasswordHideShow.setImageResource(showEyeImage)
//                    newPasswordNotVisible = 1
//                } else {
//                    binding.confirmPassword.inputType = hidePassword
//                    binding.confirmPasswordHideShow.setImageResource(hideEyeImage)
//                    newPasswordNotVisible = 0
//                }
//            }
//        }

    }
}