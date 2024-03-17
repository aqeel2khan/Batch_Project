
package com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.review_list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewModelResponse {

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
        @SerializedName("recordsTotal")
        private Long mRecordsTotal;
        @SerializedName("status")
        private String mStatus;

        public List<Internaldatum> getInternaldata() {
            return mInternaldata;
        }

        public void setInternaldata(List<Internaldatum> internaldata) {
            mInternaldata = internaldata;
        }

        public Long getRecordsTotal() {
            return mRecordsTotal;
        }

        public void setRecordsTotal(Long recordsTotal) {
            mRecordsTotal = recordsTotal;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

    }

    public class Internaldatum {

        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("dish_id")
        private Long mDishId;
        @SerializedName("id")
        private Long mId;
        @SerializedName("rating")
        private Long mRating;
        @SerializedName("review")
        private String mReview;
        @SerializedName("updated_at")
        private String mUpdatedAt;
        @SerializedName("user_id")
        private Long mUserId;
        @SerializedName("user_name")
        private String mUserName;

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public Long getDishId() {
            return mDishId;
        }

        public void setDishId(Long dishId) {
            mDishId = dishId;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public Long getRating() {
            return mRating;
        }

        public void setRating(Long rating) {
            mRating = rating;
        }

        public String getReview() {
            return mReview;
        }

        public void setReview(String review) {
            mReview = review;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public Long getUserId() {
            return mUserId;
        }

        public void setUserId(Long userId) {
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
