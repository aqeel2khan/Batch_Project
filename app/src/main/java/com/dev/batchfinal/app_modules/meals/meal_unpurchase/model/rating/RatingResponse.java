
package com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.rating;

import com.google.gson.annotations.SerializedName;


public class RatingResponse {

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
        private Internaldata mInternaldata;
        @SerializedName("status")
        private String mStatus;

        public Internaldata getInternaldata() {
            return mInternaldata;
        }

        public void setInternaldata(Internaldata internaldata) {
            mInternaldata = internaldata;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

    }

    public class Internaldata {

        @SerializedName("dish_id")
        private String mDishId;
        @SerializedName("rating")
        private String mRating;
        @SerializedName("review")
        private String mReview;
        @SerializedName("user_id")
        private String mUserId;
        @SerializedName("user_name")
        private String mUserName;

        public String getDishId() {
            return mDishId;
        }

        public void setDishId(String dishId) {
            mDishId = dishId;
        }

        public String getRating() {
            return mRating;
        }

        public void setRating(String rating) {
            mRating = rating;
        }

        public String getReview() {
            return mReview;
        }

        public void setReview(String review) {
            mReview = review;
        }

        public String getUserId() {
            return mUserId;
        }

        public void setUserId(String userId) {
            mUserId = userId;
        }

        public String getUserName() {
            return mUserName;
        }

        public void setUserName(String userName) {
            mUserName = userName;
        }

    }

    public class Error {


    }

}
