
package com.example.bottomanimationmydemo.model.chosen_meal_details_model;

import com.google.gson.annotations.SerializedName;


public class DishDetailsRequest {

    @SerializedName("dish_id")
    private String mDishId;
    @SerializedName("goal_id")
    private String mGoalId;
    @SerializedName("meal_id")
    private String mMealId;

    public String getDishId() {
        return mDishId;
    }

    public void setDishId(String dishId) {
        mDishId = dishId;
    }

    public String getGoalId() {
        return mGoalId;
    }

    public void setGoalId(String goalId) {
        mGoalId = goalId;
    }

    public String getMealId() {
        return mMealId;
    }

    public void setMealId(String mealId) {
        mMealId = mealId;
    }

}
