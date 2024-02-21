package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.course_model.ListData

interface CourseListItemPosition<T> {

    fun onCourseListItemPosition(item: ListData, position: T)
}