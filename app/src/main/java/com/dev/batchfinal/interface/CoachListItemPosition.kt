package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.coach_list_model.Data

interface CoachListItemPosition<T> {

    fun onCoachListItemPosition(item: Data, position: T)
}