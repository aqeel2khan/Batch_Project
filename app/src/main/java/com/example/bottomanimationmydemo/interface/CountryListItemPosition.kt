package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.meal_detail_model.Category

interface CountryListItemPosition<T> {

    fun onCountryListItemPosition(item: Category, position: T)
}