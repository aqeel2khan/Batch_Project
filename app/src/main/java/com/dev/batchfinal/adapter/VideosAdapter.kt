package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerStateListener
import com.dev.batchfinal.*
import com.dev.batchfinal.app_utils.VimeoExtractor
import com.dev.batchfinal.app_utils.VimeoExtractor.OnExtractionListener
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
        fun bind(videoItem: Course_duration_exercise, position: Int)
        {
            val videDetail = videoItem.video_detail
            try {
                /**
                 *
                 * OTHER VIDEO LAYER MATERIALS CONTENT@BBh
                 *
                 * */
                binding.tvVideoTitle.text = videDetail.video_title
                binding.tvVideoOutOff.text = position.toString() + "/" + videoItems.size.toString()

                if (videoItems.size > 0) {
                    val innerAdapter = InnerAdapter(mContext, videoItems, position)
                    binding.rvVideoCurrentPos.layoutManager = LinearLayoutManager(mContext)
                    binding.rvVideoCurrentPos.adapter = innerAdapter
                }
                binding.closeVideoMode.setOnClickListener {
                    listener.onCloseVideoView(videoItem, position)
                }
                binding.vedioInformation.setOnClickListener {
                    listener.onClickVideoInformation(videoItem, position)

                }
                /**
                 *
                 *
                 *PLAY-WITH VIMEO PLAYER @BBh
                 *
                 * */

                val vimeoVideoKey = videDetail.video_id.toInt()

                 videDetail.player_embed_url
                 Log.e("VimeoEmbededURL",  videDetail.player_embed_url)
                binding.vimeoPlayerView.initialize(true, vimeoVideoKey)


                //binding.vimeoPlayerView.initialize(true, 925514545)
                //binding.vimeoPlayerView.setAspectRatio(9.0 / 16.0); // Portrait || Landsacpe aspect ratio -(16:9)

                binding.tvVideoTitle.text = videDetail.video_title
                 binding.tvVideoOutOff.text = position.toString() + "/" + videoItems.size.toString()

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
                //Test for Extracting URL from vimeo vds key
                /*   val vimeoUrl = "https://vimeo.com/$vimeoVideoKey"
                     VimeoExtractor.extractVideoUrl(vimeoUrl, object : OnExtractionListener {
                      override fun onExtractionComplete(videoUrl: String?) {
                          // Use the extracted video URL
                          Log.d("VimeoExtractor", "Video URL: $videoUrl")
                      }

                      override fun onExtractionFailed(e: java.lang.Exception?) {
                          // Handle extraction failure
                          Log.e("VimeoExtractor", "Extraction failed: " + e!!.message)
                      }
                  })*/


                /**
                 *
                 * PLAY-WITH WEBVIEW OPTION @BBh
                 *
                 * */

              //  setupWebView(binding.vimeoPlayerView)

              //  loadVimeoUrl(binding.vimeoPlayerView, videDetail.player_embed_url)



            } catch (_: Exception) {
            }




            binding.btnFinishWorkout.setOnClickListener {
                listener.onPositionItemVideoFinish(videoItem, position)

            }


        }

    }

    private fun loadVimeoUrl(vimeoPlayerView: WebView, playerEmbedUrl: String) {
        try {

            vimeoPlayerView.loadUrl(playerEmbedUrl)
            //vimeoPlayerView.loadData(playerEmbedUrl, "text/html", "utf-8");


        } catch (_: Exception) {
        }
    }

    private fun setupWebView(vimeoPlayerView: WebView) {
        try {
            val webSettings: WebSettings = vimeoPlayerView.settings
            webSettings.javaScriptEnabled = true // Enable JavaScript
            webSettings.domStorageEnabled = true // Enable DOM Storage
            webSettings.mediaPlaybackRequiresUserGesture = true // Allow autoplay of media
            // vimeoPlayerView.webChromeClient = WebChromeClient() // Handle video playback controls
            vimeoPlayerView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress == 100) {

                        vimeoPlayerView.loadUrl(
                            "javascript:(function() { " +
                                    "var player = document.querySelector('iframe');" +
                                    "var playerOrigin = '*';" +
                                    "player.contentWindow.postMessage(JSON.stringify({ method: 'setLoop', value: false }), playerOrigin);" +
                                    "player.contentWindow.postMessage(JSON.stringify({ method: 'setAutopause', value: false }), playerOrigin);" +
                                    "player.style.width = '100%';" +
                                    "player.style.height = '800px';" +
                                    "var vimeoControls = player.contentDocument.getElementsByClassName('controls');" +
                                    "if (vimeoControls.length > 0) {" +
                                    "  vimeoControls[0].style.display = 'none';" +  // Hide the controls bar
                                    "}" +
                                    "})()"
                        )
                        /*             val script = """
                            (function() {
                            var player = document.querySelector('iframe');
                            var playerOrigin = '*';
                             player.contentWindow.postMessage(JSON.stringify({ method: 'setLoop', value: false }), playerOrigin);
                    player.contentWindow.postMessage(JSON.stringify({ method: 'setAutopause', value: false }), playerOrigin);
               var vimeoControls = player.contentDocument.getElementsByClassName('controls');
               if (vimeoControls.length > 0) {
                   vimeoControls[0].style.display = 'none'; // Hide the controls bar
               }
               player.style.width = '100%'; // Set width to 100% of the container
               player.style.height = '400px'; // Set height to 400 pixels (adjust as needed)/400px
           })()
       """.trimIndent()

                       vimeoPlayerView.post {
                           vimeoPlayerView.evaluateJavascript(script, null)
                       }*/


                    }
                }
            }

        } catch (_: Exception) {
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

