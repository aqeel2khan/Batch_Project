package com.dev.batchfinal.app_utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.dev.batchfinal.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

object MyCustom {

    fun parseErrorBody(context: Context, error: String?, stringValue: String):String {
        try {
            val jsonObject = JSONObject(error)
            val error = jsonObject.optJSONObject("error")
            val msg = jsonObject.optString("message")
            when (stringValue) {
                "emp_list" -> {}
                "create_company" -> {}
                "schedulerSetup" -> {}
                "emp_setup" -> {}
                "docUpload" -> {}
                else -> {
                    // context.showToast(msg.toString())
                    return msg
                }
            }

            Log.d("loginMsg", msg)
            val iterator = error.keys()
            while (iterator.hasNext()) {
                val key = iterator.next().toString()
                val errorValue = error.optString(key)
                val errorValue_new = errorValue.replace("[", "").replace("]", "");
                //  context.showToast(errorValue_new)
                return errorValue_new
                Log.d("loginMsg++++", errorValue_new)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return  "Some error occurred"
    }




    fun errorBody(context: Context, error: String?, stringValue: String){
        try {
            val jsonObject = JSONObject(error)
            val error = jsonObject.optJSONObject("error")
            val msg = jsonObject.optString("message")
            when (stringValue) {
                "emp_list" -> {}
                "create_company" -> {}
                "schedulerSetup" -> {}
                "emp_setup" -> {}
                "docUpload" -> {}
                else -> {
                    context.showToast(msg.toString())
                }
            }

            Log.d("loginMsg", msg)
            val iterator = error.keys()
            while (iterator.hasNext()) {
                val key = iterator.next().toString()
                val errorValue = error.optString(key)
                val errorValue_new = errorValue.replace("[", "").replace("]", "");
                context.showToast(errorValue_new)
                Log.d("loginMsg++++", errorValue_new)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun errorBodyFile(context: Context,error: String?){
        try {
            val jsonObject = JSONObject(error)
            val error = jsonObject.optJSONObject("error")
            val msg = jsonObject.optString("message")
            context.showToast(msg.toString())
            val iterator = error.keys()
            while (iterator.hasNext()) {
                val key = iterator.next().toString()
                val errorValue = error.optString(key)
                val errorValue_new = errorValue.replace("[", "").replace("]", "");
                context.showToast(errorValue_new)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            context.showToast("The uploaded file format doesn't match the expected format.")
        }
    }

    fun compareDatesUsingDate(startDate: Date, endDate: Date): Int {
        return startDate.compareTo(endDate)
    }

    fun convertStringToDate(dateString: String, dateFormat: String): Date {
        val formatter = SimpleDateFormat(dateFormat)
        return formatter.parse(dateString)
    }

    fun loadImage(view: ImageView, url: String?) {
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
    }

    fun currentTime(): String{
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        return currentTime
    }

    fun CurrentDate(): String{
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return currentDate
    }

    fun getSortedStringColor(colorCode: String?): String {
        val withoutFirstCharacter = colorCode?.substring(1)
        val withoutLastCharacter  = withoutFirstCharacter?.substring(0, withoutFirstCharacter.length - 1)
        val list: List<String> = listOf(*withoutLastCharacter?.split(",")!!.toTypedArray())
        val r = list[0]
        val g = list[1]
        val b = list[2]
        return String.format("#%02x%02x%02x", Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b))
    }

    fun hideKeyboard(requireActivity: FragmentActivity) {
        val inputManager = requireActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(0, 0)
    }
}