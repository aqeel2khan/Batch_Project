package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.coach_list_model.Data

interface CoachListItemPosition<T> {

    fun onCoachListItemPosition(item: Data, position: T)
}