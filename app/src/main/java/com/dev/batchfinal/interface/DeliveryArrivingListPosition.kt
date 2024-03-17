package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_arriving.DeliveryArrivingResponse



interface DeliveryArrivingListPosition<T> {

    fun onCategoryListItemPosition(item: DeliveryArrivingResponse.Internaldatum, position: T)
}