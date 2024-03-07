package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.meal_detail_model.Category

interface CountryListItemPosition<T> {

    fun onCountryListItemPosition(item: Category, position: T)
}