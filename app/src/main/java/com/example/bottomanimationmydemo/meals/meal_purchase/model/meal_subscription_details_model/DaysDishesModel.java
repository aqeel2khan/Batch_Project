
package com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_subscription_details_model;

import com.google.gson.annotations.SerializedName;

public class DaysDishesModel {

    @SerializedName("day")
    private String mDay;
    @SerializedName("dish_category")
    private String mDishCategory;
    @SerializedName("dish_id")
    private String mDishId;
    @SerializedName("dish_image")
    private String mDishImage;
    @SerializedName("dish_name")
    private String mDishName;
    @SerializedName("month")
    private String mMonth;
    @SerializedName("selected")
    private String mSelected;

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getDishCategory() {
        return mDishCategory;
    }

    public void setDishCategory(String dishCategory) {
        mDishCategory = dishCategory;
    }

    public String getDishId() {
        return mDishId;
    }

    public void setDishId(String dishId) {
        mDishId = dishId;
    }

    public String getDishImage() {
        return mDishImage;
    }

    public void setDishImage(String dishImage) {
        mDishImage = dishImage;
    }

    public String getDishName() {
        return mDishName;
    }

    public void setDishName(String dishName) {
        mDishName = dishName;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getSelected() {
        return mSelected;
    }

    public void setSelected(String selected) {
        mSelected = selected;
    }

}
