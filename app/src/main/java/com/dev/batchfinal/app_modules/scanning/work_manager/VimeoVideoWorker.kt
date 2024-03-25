package com.dev.batchfinal.app_modules.scanning.work_manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.lang.reflect.Type


class VimeoVideoWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    val dataListLiveData: MutableLiveData<ArrayList<HashMap<String, String>>> = MutableLiveData()

    override suspend fun doWork(): Result {
        val vimeoVideoURLlist=ArrayList<HashMap<String,String>>()

        val serializedData = inputData.getString("dataList")

        // Deserialize the data
        val dataListType: Type = object : TypeToken<ArrayList<HashMap<String, String>>>() {}.type
        val dataList: ArrayList<HashMap<String, String>> = Gson().fromJson(serializedData, dataListType)

        // Perform your tasks with dataList
        // Example:

        for (data in dataList) {
            // Do something with each HashMap
           // val videoKey = inputData.getString("videoKey")
           // val videoUrl = fetchVideoUrl(videoKey)
            val videoUrl= data["videoUrl"]
            val videoKey= data["videoKey"]
            val videoID= data["videoId"]
            val map= fetchVideoUrl(videoUrl,videoKey,videoID)
            if (map != null) {
                vimeoVideoURLlist.add(map)
            }
        }

        return if (vimeoVideoURLlist.size>0) {
            dataListLiveData.postValue(vimeoVideoURLlist)
            val serializedData = Gson().toJson(vimeoVideoURLlist)
            Log.e("serializedData",serializedData)
            val inputData = Data.Builder()
                .putString("dataList", serializedData)
                .build()
           // Result.success(workDataOf("videoUrl" to vimeoVideoURLlist))
            Result.success(inputData)
        } else {
            Result.failure()
        }
    }


    private fun fetchVideoUrl(videoUrl:String?,videoKey: String?, videoID: String?): HashMap<String,String>? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://player.vimeo.com/video/$videoUrl/config")
               // .addHeader("Cookie", "__cf_bm=keaEhVR_5gOrucLlFi8lLMRjMRMXoXTe_9tcRmgcw9k-1711116492-1.0.1.1-Bgkq5YtbdtlT7rgh0.kRDg7UA2_VSXHRxq6sj1GzAJv.ADxy6992qcax.OEzSFPMJhBh5ub3weFh0zX0Z5jUEA; _cfuvid=tCcQpuW2R11wYWRJ.p_wXiYXFenz3XXOVt.cQaH739o-1711116492978-0.0.1.1-604800000")
                .build()
            val response = client.newCall(request).execute()
            var mResponse="RESPONSE ERROR"
            val map=HashMap<String,String>()

            if (response.isSuccessful)
            {
                Log.e("RESPONSE MSG",response.message)
                Log.e("RESPONSE BODY",response.body.toString())
                var mJsonData=JSONObject(response.body!!.string())
               // Log.e("mJsonData",mJsonData.toString())
                val mJsonDataVideo=JSONObject(mJsonData.getJSONObject("video").toString())
                val mVideoStr=mJsonDataVideo.getString("embed_code").toString()
                Log.e("embed_code",mVideoStr)
                val matcher: Matcher = Pattern.compile("src=\"([^\"]+)\"").matcher(mVideoStr)
                matcher.find()
                val src = matcher.group(1)
                Log.e("VIMEO URL",src)
                map["videoUrl"] = src.toString()
                map["videoKey"] = videoKey.toString()
                map["videoId"] = videoID.toString()


                //return response.body.toString()
                return map

            }
            return map


        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}