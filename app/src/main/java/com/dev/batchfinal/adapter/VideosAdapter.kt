package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerStateListener
import com.dev.batchfinal.*
import com.dev.batchfinal.databinding.ItemVideoPlayBinding
import com.dev.batchfinal.`interface`.ItemVideoFinishClick
import com.dev.batchfinal.model.courseorderlist.Course_duration_exercise


// Outer adapter for the inner RecyclerView

class VideosAdapter(
    private val mContext: Context,
    private val videoItems: MutableList<Course_duration_exercise>,
    var listener: ItemVideoFinishClick<Int>
) : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemVideoPlayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation", "SetJavaScriptEnabled", "SetTextI18n")
        fun bind(videoItem: Course_duration_exercise, position: Int) {
            val videDetail = videoItem.video_detail
            try {
                //With Vimeo play
                val id = videDetail.video_id.toInt()
                //  val emabadedURL= "https://player.vimeo.com/video/$id?controls=0"
                //                binding.vimeoPlayerView.initialize(true, id,"",emabadedURL)
                /* val settings = VimeoPlayerSetting.Builder()
                     .setControls(false) // Hide controls
                     .build()*/
                // binding.vimeoPlayerView.initialize( id,settings)
                binding.vimeoPlayerView.initialize(true, id)
                binding.tvVideoTitle.text = videDetail.video_title
                binding.tvVideoOutOff.text = position.toString() + "/" + videoItems.size.toString()
                val menuCout = binding.vimeoPlayerView.menuItemCount
                Log.e("MENU COUNT", menuCout.toString())
                if (menuCout > 0) {
                    binding.vimeoPlayerView.removeMenuItem(menuCout)
                }
                if (videoItems.size > 0) {
                    val innerAdapter = InnerAdapter(mContext, videoItems, position)
                    binding.rvVideoCurrentPos.layoutManager = LinearLayoutManager(mContext)
                    binding.rvVideoCurrentPos.adapter = innerAdapter
                }

                binding.vimeoPlayerView.addStateListener(object : VimeoPlayerStateListener {
                    override fun onPlaying(duration: Float) {
                    }

                    override fun onPaused(seconds: Float) {
                    }

                    override fun onEnded(duration: Float) {
                        Log.d("TAG", "onCompletion ")
                    }
                })
                binding.closeVideoMode.setOnClickListener {
                    listener.onCloseVideoView(videoItem, position)
                }
                binding.vedioInformation.setOnClickListener {
                    listener.onClickVideoInformation(videoItem, position)

                }
                //With webview option
                /* val webSettings: WebSettings = binding.vimeoPlayerView.settings
                 webSettings.javaScriptEnabled = true
                 val videoId = videDetail.video_id.toInt()
                 val iframeEmbedCode = "<iframe src=\"https://player.vimeo.com/video/$videoId?controls=0\" width=\"640\" height=\"360\" frameborder=\"0\" allowfullscreen></iframe>"
                 binding.vimeoPlayerView.loadData(iframeEmbedCode, "text/html", "utf-8");
                 binding.vimeoPlayerView.webChromeClient = object : WebChromeClient() {
                     override fun onProgressChanged(view: WebView, newProgress: Int) {
                         super.onProgressChanged(view, newProgress)
                         if (newProgress == 100) {
                             binding.vimeoPlayerView.loadUrl(
                                 "javascript:(function() { " +
                                         "var player = document.querySelector('iframe');" +
                                         "var playerOrigin = '*';" +
                                         "player.contentWindow.postMessage(JSON.stringify({ method: 'setLoop', value: false }), playerOrigin);" +
                                         "player.contentWindow.postMessage(JSON.stringify({ method: 'setAutopause', value: false }), playerOrigin);" +
                                         "var vimeoControls = player.contentDocument.getElementsByClassName('controls');" +
                                         "if (vimeoControls.length > 0) {" +
                                         "  vimeoControls[0].style.display = 'none';" +  // Hide the controls bar
                                         "}" +
                                         "})()"
                             )
                         }
                     }
                 }*/


            } catch (_: Exception) {
            }




            binding.btnFinishWorkout.setOnClickListener {
                listener.onPositionItemVideoFinish(videoItem, position)

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemVideoPlayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoItems[position], position)

    }

}

// Inner adapter for the inner RecyclerView
class InnerAdapter(
    private val mContext: Context,
    private val videoItems: MutableList<Course_duration_exercise>,
    private val currentPlyingPost: Int
) :
    RecyclerView.Adapter<InnerAdapter.ViewHolder?>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_position, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        // val item = videoItems[position]

        if (position <= videoItems.size - 1) {
            holder.viewItem.setBackgroundColor(mContext.resources.getColor(R.color.white))
            if (currentPlyingPost == position) {
                holder.viewItem.setBackgroundColor(mContext.resources.getColor(R.color.greenColor))

            }

        }
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewItem: View

        init {
            viewItem = itemView.findViewById<View>(R.id.videoBar)
        }
    }
}

