package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.WorkoutTypeAdapter
import com.example.bottomanimationmydemo.databinding.ActivityWeightLossBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.example.vimeoplayer2.UniversalVideoView
import com.example.vimeoplayer2.vimeoextractor.OnVimeoExtractionListener
import com.example.vimeoplayer2.vimeoextractor.VimeoExtractor
import com.example.vimeoplayer2.vimeoextractor.VimeoVideo
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeightLossActivity : BaseActivity<ActivityWeightLossBinding>(),
    UniversalVideoView.VideoViewCallback {
    private val viewModel: AllViewModel by viewModels()
    var name = ArrayList(
        Arrays.asList(
            "Workout Batch", "Weight Loss", "Workout Batch", "Workout Batch", "Workout Batch", "Workout Batch" )
    )
    private val TAG = "WeightLossActivity"
    private var isFullscreen = false
    private var cachedHeight = 0
    private val SEEK_POSITION_KEY = "SEEK_POSITION_KEY"
    private var mSeekPosition = 0
    private val VIMEO_VIDEO_URL = "https://player.vimeo.com/video/1084537"

    /*https://vimeo.com/347119375,
    * https://vimeo.com/252443587
    * https://vimeo.com/449787858*/
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        val aniSlide: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        binding.relWeightLayout.startAnimation(aniSlide)
        setWorkoutTypeAdapter()
        setVideoViewData()
    }

    private fun setVideoViewData() {
        binding.videoView.setMediaController(binding.mediaController)
        setVideoAreaSize()
        binding.videoView.setVideoViewCallback(this)
        binding.videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            Log.d(TAG, "onCompletion ")
        })

        VimeoExtractor.getInstance().fetchVideoWithURL(VIMEO_VIDEO_URL, "Batch", object :
            OnVimeoExtractionListener {
            override fun onSuccess(video: VimeoVideo) {
                var hdStream: String? = null
                for (key in video.streams.keys) {
                    hdStream = key
                }
                val hdStreamuRL = video.streams[hdStream]
                if (hdStream != null) {
                    runOnUiThread { // Start the MediaController
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

    private fun setWorkoutTypeAdapter() {
        binding.recyclerWorkoutType.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerWorkoutType.adapter = WorkoutTypeAdapter(this@WeightLossActivity, name, object : PositionItemClickListener<Int>{
            override fun onPositionItemSelected(item: String, postions: Int) {
                startActivity(Intent(this@WeightLossActivity, CourseDetailActivity::class.java))
            }

        })
    }

    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
            finish()
        }
        binding.txtChangeCourse.setOnClickListener {
         showBottomSheetDialog()
      }
      binding.startWorkout.setOnClickListener {
         startActivity(Intent(this@WeightLossActivity, WorkOutDetailScreen::class.java))
      }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        ll_bottom_change_course!!.visibility = View.VISIBLE


        dialog.show()
    }

    override fun getViewBinding() = ActivityWeightLossBinding.inflate(layoutInflater)

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause ")
        if (binding.videoView != null && binding.videoView.isPlaying()) {
            mSeekPosition = binding.videoView.getCurrentPosition()
            Log.d(TAG, "onPause mSeekPosition=$mSeekPosition")
            binding.videoView.pause()
        }
    }
    private fun setVideoAreaSize() {
        binding.videoLayout.post(Runnable {
            val width: Int = binding.videoLayout.getWidth()
            cachedHeight = (width * 405f / 720f).toInt()
            //                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
            val videoLayoutParams: ViewGroup.LayoutParams = binding.videoLayout.getLayoutParams()
            videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            videoLayoutParams.height = cachedHeight
            binding.videoLayout.setLayoutParams(videoLayoutParams)
            /*  mVideoView.setVideoPath(VIDEO_URL);
                mVideoView.requestFocus();*/
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState Position=" + binding.videoView.getCurrentPosition())
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition)
    }

    override fun onRestoreInstanceState(outState: Bundle) {
        super.onRestoreInstanceState(outState)
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY)
        Log.d(TAG, "onRestoreInstanceState Position=$mSeekPosition")
    }
    override fun onScaleChange(isFullscreen: Boolean) {
        this.isFullscreen = isFullscreen
        if (isFullscreen) {
            val layoutParams: ViewGroup.LayoutParams = binding.videoLayout.getLayoutParams()
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            binding.videoLayout.setLayoutParams(layoutParams)
//            mBottomLayout.setVisibility(View.GONE)
        } else {
            val layoutParams: ViewGroup.LayoutParams = binding.videoLayout.getLayoutParams()
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = this.cachedHeight
            binding.videoLayout.setLayoutParams(layoutParams)
//            mBottomLayout.setVisibility(View.VISIBLE)
        }
    }

    override fun onPause(mediaPlayer: MediaPlayer?) {
        Log.d(TAG, "onPause UniversalVideoView callback")
    }

    override fun onStart(mediaPlayer: MediaPlayer?) {
        Log.d(TAG, "onStart UniversalVideoView callback")
    }

    override fun onBufferingStart(mediaPlayer: MediaPlayer?) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback")
    }

    override fun onBufferingEnd(mediaPlayer: MediaPlayer?) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    override fun onBackPressed() {
        if (isFullscreen) {
            binding.videoView.setFullscreen(false)
        } else {
            super.onBackPressed()
        }
    }

}