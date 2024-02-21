package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.courseorderlist.OrderList

interface CourseOrderListItemPosition<T> {

    fun onCourseOrderListItemPosition(item: OrderList, position: T)
}