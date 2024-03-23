package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDuration


interface PositionCourseWorkoutClick<T> {

    fun onCourseWorkoutItemPosition(item: CourseDuration, position: T)
}