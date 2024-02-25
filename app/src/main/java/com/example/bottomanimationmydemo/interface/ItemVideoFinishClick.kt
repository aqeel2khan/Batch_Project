package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.VideoItem

interface ItemVideoFinishClick<T>  {

    fun onPositionItemVideoFinish(item: VideoItem, postions: T)
}