package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.courseorderlist.Course_duration


interface PositionCourseWorkoutClick<T> {

    fun onCourseWorkoutItemPosition(item: Course_duration, position: T)
}