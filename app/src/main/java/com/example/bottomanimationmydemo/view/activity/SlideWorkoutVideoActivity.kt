package com.example.bottomanimationmydemo.view.activity

import androidx.activity.viewModels
import com.example.bottomanimationmydemo.adapter.VideosAdapter
import com.example.bottomanimationmydemo.databinding.ActivitySlideWorkoutVideoBinding
import com.example.bottomanimationmydemo.model.VideoItem
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlideWorkoutVideoActivity : BaseActivity<ActivitySlideWorkoutVideoBinding>() {
    private val viewModel: AllViewModel by viewModels()

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {

        val videoItems: MutableList<VideoItem> = ArrayList<VideoItem>()

        val item = VideoItem()
        item.videoURL = "https://player.vimeo.com/video/1084537"
        item.videoTitle = "Women In Tech"
        videoItems.add(item)

        val item2 = VideoItem()
        item2.videoURL = "https://player.vimeo.com/video/347119375"
        item2.videoTitle = "Sasha Solomon"
        videoItems.add(item2)

        val item3 = VideoItem()
        item3.videoURL = "https://player.vimeo.com/video/252443587"
        item3.videoTitle = "Happy Hour Wednesday"
        videoItems.add(item3)

        val item4 = VideoItem()
        item4.videoURL = "https://player.vimeo.com/video/449787858"
        item4.videoTitle = "Happy 4 Wednesday"
        videoItems.add(item4)

        binding.viewPagerVideos.setAdapter(VideosAdapter(videoItems))
    }

    override fun getViewBinding() = ActivitySlideWorkoutVideoBinding.inflate(layoutInflater)
}