
package com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_plan_subscription_update;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MealPlanSubscriptionUpdateResponse {

    @SerializedName("data")
    private Data mData;
    @SerializedName("error")
    private Error mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

    public class Data {

        @SerializedName("data")
        private List<Internaldatum> mInternaldata;
        @SerializedName("status")
        private String mStatus;

        public List<Internaldatum> getInternaldata() {
            return mInternaldata;
        }

        public void setInternaldata(List<Internaldatum> internaldata) {
            mInternaldata = internaldata;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

    }

    public class Internaldatum {

        @SerializedName("day")
        private Long mDay;
        @SerializedName("dish_id")
        private Long mDishId;
        @SerializedName("meal_id")
        private Long mMealId;
        @SerializedName("month")
        private Long mMonth;
        @SerializedName("status")
        private Long mStatus;
        @SerializedName("subscribed_id")
        private String mSubscribedId;
        @SerializedName("user_id")
        private Long mUserId;

        public Long getDay() {
            return mDay;
        }

        public void setDay(Long day) {
            mDay = day;
        }

        public Long getDishId() {
            return mDishId;
        }

        public void setDishId(Long dishId) {
            mDishId = dishId;
        }

        public Long getMealId() {
            return mMealId;
        }

        public void setMealId(Long mealId) {
            mMealId = mealId;
        }

        public Long getMonth() {
            return mMonth;
        }

        public void setMonth(Long month) {
            mMonth = month;
        }

        public Long getStatus() {
            return mStatus;
        }

        public void setStatus(Long status) {
            mStatus = status;
        }

        public String getSubscribedId() {
            return mSubscribedId;
        }

        public void setSubscribedId(String subscribedId) {
            mSubscribedId = subscribedId;
        }

        public Long getUserId() {
            return mUserId;
        }

        public void setUserId(Long userId) {
            mUserId = userId;
        }

    }
    public class Error {


    }
}
