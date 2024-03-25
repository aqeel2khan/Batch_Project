package com.dev.batchfinal.app_modules.scanning.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebSettings.PluginState
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.google.android.exoplayer2.SimpleExoPlayer


class ExoPlayerAdapter(
    private val mContext: Context,
    private val videoUrlsDataList: ArrayList<HashMap<String, String>>
) :
    RecyclerView.Adapter<ExoPlayerAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exoplayer, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoUrlsDataList[position])
    }

    override fun getItemCount(): Int {
        return videoUrlsDataList.size
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
      //  holder.releasePlayer()
        super.onViewRecycled(holder)
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  private val playerView: PlayerView = itemView.findViewById(R.id.exo_player_view)
        private var player: SimpleExoPlayer? = null
        //  private var  player = SimpleExoPlayer.Builder(mContext).build()

        /*************************************/
        //private val playerView: VimeoPlayerView = itemView.findViewById(R.id.vimeoPlayerView)

        /***********************************************/
        private val webView: WebView = itemView.findViewById(R.id.webView)

        init {

            webView.settings.javaScriptEnabled=true
            webView.settings.allowContentAccess = true
            webView.settings.setJavaScriptCanOpenWindowsAutomatically(true)
            webView.settings.setPluginState(PluginState.ON)
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
            fun bind(videoUrlsDataMap: HashMap<String, String>) {
                // player = ExoPlayerFactory.newSimpleInstance(itemView.context)
                // player= SimpleExoPlayer.Builder(itemView.context).build()

                //val dataSourceFactory = DefaultDataSourceFactory(itemView.context, "exoplayer-sample")
                //https://player.vimeo.com/video/923940893?h\u003d054ce80694


                /* val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                     .createMediaSource(Uri.parse(videoUrlsDataMap["videoKey"]))*/

                /**********************************/

                /*val mediaItem = MediaItem.fromUri(videoUrlsDataMap["videoUrl"].toString())
                 player!!.setMediaItem(mediaItem)
                 player!!.prepare()
                 player!!.playWhenReady = true
                 playerView.player = player
                 player!!.play()*/
                /**************************************/
                // playerView.initialize(true,videoUrlsDataMap["videoKey"]!!.toInt())
                /***********************************/

                webView.webChromeClient = WebChromeClient()
               // webView.loadUrl(videoUrlsDataMap["videoUrl"].toString())
                var parseURLData=videoUrlsDataMap["videoUrl"].toString().split("h\\u")
                webView.loadUrl(parseURLData[0]+"autoplay=1&loop=0&autopause=0&background=1&color=ffffff&controls=0&portrait=0%22%20width=%22800%22%20height=%20%221200%22%20%20frameborder=%220%22%20allow=%22autoplay;%20fullscreen%22%20%20allowfullscreen")

            }

            fun releasePlayer() {
                player?.release()
                player = null
            }
        private fun adjustWebViewHeight(webView: WebView) {
            val params = webView.layoutParams
            params.height = (webView.contentHeight * webView.resources.displayMetrics.density).toInt()
            webView.layoutParams = params
        }
        }
    }