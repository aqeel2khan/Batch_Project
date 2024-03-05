
package com.example.bottomanimationmydemo.model.subscribe_list_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MealSubscribeListResponse {

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
        private List<InternalDatum> mInternalData;
        @SerializedName("recordsTotal")
        private Long mRecordsTotal;
        @SerializedName("status")
        private String mStatus;

        public List<InternalDatum> getInternalData() {
            return mInternalData;
        }

        public void setInternalData(List<InternalDatum> internalData) {
            mInternalData = internalData;
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

    public class InternalDatum {

        @SerializedName("avg_cal_per_day")
        private String mAvgCalPerDay;
        @SerializedName("batch_id")
        private Long mBatchId;
        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("description_ar")
        private String mDescriptionAr;
        @SerializedName("discount")
        private Long mDiscount;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("goal_id")
        private Long mGoalId;
        @SerializedName("id")
        private Long mId;
        @SerializedName("image")
        private Object mImage;
        @SerializedName("meal_count")
        private Long mMealCount;
        @SerializedName("meal_tags")
        private Object mMealTags;
        @SerializedName("name")
        private String mName;
        @SerializedName("name_ar")
        private String mNameAr;
        @SerializedName("price")
        private String mPrice;
        @SerializedName("restaurant_id")
        private Long mRestaurantId;
        @SerializedName("status")
        private Long mStatus;
        @SerializedName("subscribed_id")
        private Long mSubscribedId;
        @SerializedName("updated_at")
        private String mUpdatedAt;

        public String getAvgCalPerDay() {
            return mAvgCalPerDay;
        }

        public void setAvgCalPerDay(String avgCalPerDay) {
            mAvgCalPerDay = avgCalPerDay;
        }

        public Long getBatchId() {
            return mBatchId;
        }

        public void setBatchId(Long batchId) {
            mBatchId = batchId;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getDescriptionAr() {
            return mDescriptionAr;
        }

        public void setDescriptionAr(String descriptionAr) {
            mDescriptionAr = descriptionAr;
        }

        public Long getDiscount() {
            return mDiscount;
        }

        public void setDiscount(Long discount) {
            mDiscount = discount;
        }

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public Long getGoalId() {
            return mGoalId;
        }

        public void setGoalId(Long goalId) {
            mGoalId = goalId;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public Object getImage() {
            return mImage;
        }

        public void setImage(Object image) {
            mImage = image;
        }

        public Long getMealCount() {
            return mMealCount;
        }

        public void setMealCount(Long mealCount) {
            mMealCount = mealCount;
        }

        public Object getMealTags() {
            return mMealTags;
        }

        public void setMealTags(Object mealTags) {
            mMealTags = mealTags;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getNameAr() {
            return mNameAr;
        }

        public void setNameAr(String nameAr) {
            mNameAr = nameAr;
        }

        public String getPrice() {
            return mPrice;
        }

        public void setPrice(String price) {
            mPrice = price;
        }

        public Long getRestaurantId() {
            return mRestaurantId;
        }

        public void setRestaurantId(Long restaurantId) {
            mRestaurantId = restaurantId;
        }

        public Long getStatus() {
            return mStatus;
        }

        public void setStatus(Long status) {
            mStatus = status;
        }

        public Long getSubscribedId() {
            return mSubscribedId;
        }

        public void setSubscribedId(Long subscribedId) {
            mSubscribedId = subscribedId;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

    }
    public class Error {


    }

}