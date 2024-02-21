package com.example.bottomanimationmydemo.adapter

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemAllBatchesBinding
import com.example.bottomanimationmydemo.databinding.ItemVideoPlayBinding
import com.example.bottomanimationmydemo.model.VideoItem
import com.example.vimeoplayer2.vimeoextractor.OnVimeoExtractionListener
import com.example.vimeoplayer2.vimeoextractor.VimeoExtractor
import com.example.vimeoplayer2.vimeoextractor.VimeoVideo

class VideosAdapter(val videoItems: MutableList<VideoItem>): RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemVideoPlayBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(videoItem: VideoItem, position: Int) {
            binding.videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                Log.d("TAG", "onCompletion ")
            })
            VimeoExtractor.getInstance().fetchVideoWithURL(videoItem.videoURL, "Batch", object :
                OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    var hdStream: String? = null
                    for (key in video.streams.keys) {
                        hdStream = key
                    }
                    val hdStreamuRL = video.streams[hdStream]
                    if (hdStream != null) {
                        (binding.root.context as Activity).runOnUiThread { // Start the MediaController
                            binding.videoView.setMediaController(binding.mediaController)
                            // Get the URL from String VideoURL
                            val video = Uri.parse(hdStreamuRL)
                            binding.videoView.setVideoURI(video)
                        }
                    }
                }

                //                setLink(hdStream);
                //...
                //            }
                override fun onFailure(throwable: Throwable) {
                    //Error handling here
                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoPlayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoItems[position], position)
    }
}