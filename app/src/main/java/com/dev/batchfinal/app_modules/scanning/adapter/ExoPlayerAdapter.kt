package com.dev.batchfinal.app_modules.scanning.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebSettings.PluginState
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.InnerAdapter
import com.dev.batchfinal.app_modules.scanning.mlistner.PlayerCallback
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDurationExercise


class ExoPlayerAdapter(
    private val mContext: Context,
    private val videoUrlsDataList: ArrayList<HashMap<String, String>>,
   private val courseDurationExcerciseList: MutableList<CourseDurationExercise>,
   private var listener: PlayerCallback
) :
    RecyclerView.Adapter<ExoPlayerAdapter.VideoViewHolder>() {

        init {
            this.listener=listener
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exoplayer, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
      val vKey= videoUrlsDataList[position]["videoKey"]
        for (i in courseDurationExcerciseList)
        { Log.e("ID VERIFICATION","vKey "+vKey+" vDetailVideoKey "+i.videoDetail.videoId)

            if(vKey==i.videoDetail.videoId)
           {
               holder.bind(position,videoUrlsDataList[position],videoUrlsDataList,courseDurationExcerciseList[position])

              // exitProcess(0)
           }
        }

    }

    override fun getItemCount(): Int {
        return videoUrlsDataList.size
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
    }

    @SuppressLint("SetJavaScriptEnabled")
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val webView: WebView = itemView.findViewById(R.id.webView)
        private val tvVideoTitle: TextView = itemView.findViewById(R.id.tvVideoTitle)
        private val tvVideoOutOff: TextView = itemView.findViewById(R.id.tvVideoOutOff)
        private val vedioInformation: ImageView = itemView.findViewById(R.id.vedioInformation)
        private val closePlayer: ImageView = itemView.findViewById(R.id.closeVideoMode)
        private val rvVideoCurrentPos:RecyclerView = itemView.findViewById(R.id.rvVideoCurrentPos)
        init {

            webView.settings.javaScriptEnabled=true
            webView.settings.allowContentAccess = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.pluginState = PluginState.ON
            webView.settings.userAgentString = "0";//for desktop 1 or mobile 0.
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
                }
            }
            webView.settings.mediaPlaybackRequiresUserGesture = false
            webView.webChromeClient = WebChromeClient()
            webView.settings.domStorageEnabled = true
            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE


        }

            @SuppressLint("SetJavaScriptEnabled")
            fun bind(
                position: Int,
                videoUrlsDataMap: HashMap<String, String>,
                videoUrlsDataList: ArrayList<HashMap<String, String>>,
                courseDurationExercise: CourseDurationExercise
            ) {

                tvVideoTitle.text=courseDurationExercise.title
                tvVideoOutOff.text = position.toString() + "/" + videoUrlsDataMap.size.toString()
                webView.webChromeClient = WebChromeClient()
               // webView.loadUrl(videoUrlsDataMap["videoUrl"].toString())
                val parseURLData=videoUrlsDataMap["videoUrl"].toString().split("h\\u")
                webView.loadUrl(parseURLData[0]+"autoplay=1&loop=0&autopause=0&background=1&color=ffffff&controls=0&portrait=0%22%20width=%22800%22%20height=%20%221200%22%20%20frameborder=%220%22%20allow=%22autoplay;%20fullscreen%22%20%20allowfullscreen")
                if (videoUrlsDataList.size > 0) {
                    val innerAdapter = InnerAdapter(mContext, videoUrlsDataList, position)
                    rvVideoCurrentPos.layoutManager = LinearLayoutManager(mContext)
                    rvVideoCurrentPos.adapter = innerAdapter
                }
                closePlayer.setOnClickListener {
                    listener.onClickClosePlayer(position)
                }
                vedioInformation.setOnClickListener {
                    listener.onClickVideoInformation(position,courseDurationExercise)

                }
            }

        private fun adjustWebViewHeight(webView: WebView) {
            val params = webView.layoutParams
            params.height = (webView.contentHeight * webView.resources.displayMetrics.density).toInt()
            webView.layoutParams = params
        }
        }
    }




// Inner adapter to show SIDE-BAR
class InnerAdapter(
    private val mContext: Context,
    private val videoUrlsDataList: ArrayList<HashMap<String, String>>,
    private val currentPlyingPost: Int
) :
    RecyclerView.Adapter<InnerAdapter.ViewHolder?>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): InnerAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_position, parent, false)
        return InnerAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InnerAdapter.ViewHolder, position: Int) {
        // val item = videoItems[position]

        if (position <= videoUrlsDataList.size - 1) {
            holder.viewItem.setBackgroundColor(mContext.resources.getColor(R.color.white))
            if (currentPlyingPost == position) {
                holder.viewItem.setBackgroundColor(mContext.resources.getColor(R.color.greenColor))

            }
        }
    }

    override fun getItemCount(): Int {
        return videoUrlsDataList.size
    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewItem: View

        init {
            viewItem = itemView.findViewById<View>(R.id.videoBar)
        }
    }


}