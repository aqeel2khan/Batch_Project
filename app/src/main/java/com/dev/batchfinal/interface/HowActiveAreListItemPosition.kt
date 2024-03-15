package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.question.model.HowActiveYouModel


interface HowActiveAreListItemPosition<T> {

    fun onHowActiveAreListItemPosition(item: HowActiveYouModel, position: T)
}