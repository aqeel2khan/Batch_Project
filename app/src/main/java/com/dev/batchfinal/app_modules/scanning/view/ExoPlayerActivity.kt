package com.dev.batchfinal.app_modules.scanning.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_modules.scanning.adapter.ExoPlayerAdapter
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.databinding.ActivityExoplayerBinding
import com.dev.batchfinal.databinding.ActivityLoginBinding
import com.dev.batchfinal.model.courseorderlist.Course_duration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExoPlayerActivity :AppBaseActivity<ActivityExoplayerBinding>() {
    private lateinit var  dataList: ArrayList<HashMap<String, String>>
    override fun getViewBinding() = ActivityExoplayerBinding.inflate(layoutInflater)
    override fun initUI() {
        try {
            val gson = Gson()
            var jsonStr= ""
            if(intent.hasExtra("video_link")){
                jsonStr = intent.getStringExtra("video_link").toString()
            }
            if(jsonStr.isNotEmpty()){
                dataList = jsonStringToArrayListHashMap(jsonStr)
            }


          //  dataList= intent.getSerializableExtra("dataList") as ArrayList<HashMap<String, String>>


        }catch (_:Exception){}


    }

    override fun onStart() {
        super.onStart()
        val mPlayerAdapter = ExoPlayerAdapter(this,dataList )
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.rvExoPlayer.layoutManager = manager
        binding.rvExoPlayer.adapter = mPlayerAdapter
    }
    private fun jsonStringToArrayListHashMap(jsonString: String): ArrayList<HashMap<String, String>> {
        val type = object : TypeToken<ArrayList<HashMap<String, String>>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}