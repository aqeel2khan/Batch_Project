package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.course_model.ListData

interface CourseListItemPosition<T> {

    fun onCourseListItemPosition(item: ListData, position: T)
}