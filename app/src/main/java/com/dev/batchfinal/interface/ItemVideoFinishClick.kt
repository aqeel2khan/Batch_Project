package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.courseorderlist.Course_duration_exercise

interface ItemVideoFinishClick<T>  {

    fun onPositionItemVideoFinish(item: Course_duration_exercise, postions: T)
}