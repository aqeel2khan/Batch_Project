package com.dev.batchfinal.app_modules.scanning.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.regex.Matcher
import java.util.regex.Pattern


class VimeoVideoWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val videoKey = inputData.getString("videoKey")
        val videoUrl = fetchVideoUrl(videoKey)

        return if (videoUrl != null) {
            Result.success(workDataOf("videoUrl" to videoUrl))
        } else {
            Result.failure()
        }
    }


    private fun fetchVideoUrl(videoKey: String?): String? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://player.vimeo.com/video/$videoKey/config")
               // .addHeader("Cookie", "__cf_bm=keaEhVR_5gOrucLlFi8lLMRjMRMXoXTe_9tcRmgcw9k-1711116492-1.0.1.1-Bgkq5YtbdtlT7rgh0.kRDg7UA2_VSXHRxq6sj1GzAJv.ADxy6992qcax.OEzSFPMJhBh5ub3weFh0zX0Z5jUEA; _cfuvid=tCcQpuW2R11wYWRJ.p_wXiYXFenz3XXOVt.cQaH739o-1711116492978-0.0.1.1-604800000")
                .build()
            val response = client.newCall(request).execute()
            var mResponse="RESPONSE ERROR"
            if (response.isSuccessful)
            {
                Log.e("RESPONSE MSG",response.message)
                Log.e("RESPONSE BODY",response.body.toString())
                var mJsonData=JSONObject(response.body!!.string())
                Log.e("mJsonData",mJsonData.toString())
                val mJsonDataVideo=JSONObject(mJsonData.getJSONObject("video").toString())
                val mVideoStr=mJsonDataVideo.getString("embed_code").toString()
                Log.e("embed_code",mVideoStr)
                val matcher: Matcher = Pattern.compile("src=\"([^\"]+)\"").matcher(mVideoStr)
                matcher.find()
                val src = matcher.group(1)
                Log.e("VIMEO URL",src)



                return response.body.toString()

            }else
            {
                return response.message.toString()

            }
            return mResponse


        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}