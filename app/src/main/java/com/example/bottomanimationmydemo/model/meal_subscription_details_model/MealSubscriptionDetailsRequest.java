
package com.example.bottomanimationmydemo.model.meal_subscription_details_model;

import com.google.gson.annotations.SerializedName;


public class MealSubscriptionDetailsRequest {

    @SerializedName("meal_id")
    private String mMealId;
    @SerializedName("subscribed_id")
    private String mSubscribedId;
    @SerializedName("user_id")
    private String mUserId;

    public String getMealId() {
        return mMealId;
    }

    public void setMealId(String mealId) {
        mMealId = mealId;
    }

    public String getSubscribedId() {
        return mSubscribedId;
    }

    public void setSubscribedId(String subscribedId) {
        mSubscribedId = subscribedId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
