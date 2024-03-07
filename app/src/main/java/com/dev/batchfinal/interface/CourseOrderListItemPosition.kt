package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.courseorderlist.OrderList

interface CourseOrderListItemPosition<T> {

    fun onCourseOrderListItemPosition(item: OrderList, position: T)
}