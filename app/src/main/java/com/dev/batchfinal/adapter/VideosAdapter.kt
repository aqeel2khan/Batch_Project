package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerStateListener
import com.dev.batchfinal.databinding.ItemVideoPlayBinding
//import com.dev.batchfinal.databinding.ItemVideoPlayBinding
import com.dev.batchfinal.`interface`.ItemVideoFinishClick
import com.dev.batchfinal.model.courseorderlist.Course_duration_exercise

class VideosAdapter(
    val videoItems: MutableList<Course_duration_exercise>,
    var listener: ItemVideoFinishClick<Int>
) : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemVideoPlayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation")
        fun bind(videoItem: Course_duration_exercise, position: Int) {

            if(videoItem!=null){

            val videDetail=    videoItem.video_detail
                if(videDetail!=null){

                    val id =    videDetail.video_id.toInt()

                    binding.vimeoPlayerView.initialize(true, id)

                }

            }

            binding.vimeoPlayerView.addStateListener(object : VimeoPlayerStateListener {
                override fun onPlaying(duration: Float) {
//                            binding.playerStateTextView.text = getString(R.string.player_state, "onPlaying")
                }

                override fun onPaused(seconds: Float) {
//                            binding.playerStateTextView.text = getString(R.string.player_state, "onPaused")
                }

                override fun onEnded(duration: Float) {
                          Log.d("TAG", "onCompletion ")
                }
            })



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