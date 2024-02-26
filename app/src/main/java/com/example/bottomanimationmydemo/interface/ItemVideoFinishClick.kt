package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.VideoItem
import com.example.bottomanimationmydemo.model.courseorderlist.Course_duration_exercise

interface ItemVideoFinishClick<T>  {

    fun onPositionItemVideoFinish(item: Course_duration_exercise, postions: T)
}