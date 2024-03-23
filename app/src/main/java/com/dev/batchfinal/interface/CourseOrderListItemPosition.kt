package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.scanning.model.course_order_list.List

interface CourseOrderListItemPosition<T> {

    fun onCourseOrderListItemPosition(item: List, position: T)
}