package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.meal_detail_model.Category

interface CategoryListItemPosition<T> {

    fun onCategoryListItemPosition(item: Category, position: T)
}