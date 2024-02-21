package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.courseorderlist.Course_duration


interface PositionCourseWorkoutClick<T> {

    fun onCourseWorkoutItemPosition(item: Course_duration, position: T)
}