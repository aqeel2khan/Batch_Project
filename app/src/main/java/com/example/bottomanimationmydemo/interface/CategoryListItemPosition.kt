package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.meal_detail_model.Category

interface CategoryListItemPosition<T> {

    fun onCategoryListItemPosition(item: Category, position: T)
}