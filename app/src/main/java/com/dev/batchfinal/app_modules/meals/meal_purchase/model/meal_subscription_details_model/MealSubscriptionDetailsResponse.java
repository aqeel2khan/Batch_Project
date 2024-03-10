
package com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_subscription_details_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MealSubscriptionDetailsResponse {

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
        private InternalData mInternalData;
        @SerializedName("status")
        private String mStatus;

        public InternalData getInternalData() {
            return mInternalData;
        }

        public void setInternalData(InternalData internalData) {
            mInternalData = internalData;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

    }

    public class InternalData {

        @SerializedName("meal_details")
        private MealDetails mMealDetails;
        @SerializedName("subscribe_detail")
        private SubscribeDetail mSubscribeDetail;

        public MealDetails getMealDetails() {
            return mMealDetails;
        }

        public void setMealDetails(MealDetails mealDetails) {
            mMealDetails = mealDetails;
        }

        public SubscribeDetail getSubscribeDetail() {
            return mSubscribeDetail;
        }

        public void setSubscribeDetail(SubscribeDetail subscribeDetail) {
            mSubscribeDetail = subscribeDetail;
        }

    }

    public class MealDetails {

        @SerializedName("avg_cal_per_day")
        private String mAvgCalPerDay;
        @SerializedName("category_list")
        private List<CategoryList> mCategoryList;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("description_ar")
        private String mDescriptionAr;
        @SerializedName("discount")
        private Long mDiscount;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("meal_count")
        private Long mMealCount;
        @SerializedName("meal_type")
        private String mMealType;
        @SerializedName("name")
        private String mName;
        @SerializedName("name_ar")
        private String mNameAr;
        @SerializedName("price")
        private String mPrice;
        @SerializedName("snack_count")
        private Long mSnackCount;

        public String getAvgCalPerDay() {
            return mAvgCalPerDay;
        }

        public void setAvgCalPerDay(String avgCalPerDay) {
            mAvgCalPerDay = avgCalPerDay;
        }

        public List<CategoryList> getCategoryList() {
            return mCategoryList;
        }

        public void setCategoryList(List<CategoryList> categoryList) {
            mCategoryList = categoryList;
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

        public Long getMealCount() {
            return mMealCount;
        }

        public void setMealCount(Long mealCount) {
            mMealCount = mealCount;
        }

        public String getMealType() {
            return mMealType;
        }

        public void setMealType(String mealType) {
            mMealType = mealType;
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

        public Long getSnackCount() {
            return mSnackCount;
        }

        public void setSnackCount(Long snackCount) {
            mSnackCount = snackCount;
        }

    }

    public class SubscribeDetail {

        @SerializedName("days_dishes")
        private DaysDishes mDaysDishes;
        @SerializedName("end_date")
        private String mEndDate;
        @SerializedName("selected_duration")
        private Long mSelectedDuration;
        @SerializedName("start_date")
        private String mStartDate;
        @SerializedName("subscribed_id")
        private String mSubscribedId;

        public DaysDishes getDaysDishes() {
            return mDaysDishes;
        }

        public void setDaysDishes(DaysDishes daysDishes) {
            mDaysDishes = daysDishes;
        }

        public String getEndDate() {
            return mEndDate;
        }

        public void setEndDate(String endDate) {
            mEndDate = endDate;
        }

        public Long getSelectedDuration() {
            return mSelectedDuration;
        }

        public void setSelectedDuration(Long selectedDuration) {
            mSelectedDuration = selectedDuration;
        }

        public String getStartDate() {
            return mStartDate;
        }

        public void setStartDate(String startDate) {
            mStartDate = startDate;
        }

        public String getSubscribedId() {
            return mSubscribedId;
        }

        public void setSubscribedId(String subscribedId) {
            mSubscribedId = subscribedId;
        }

    }

    public class CategoryList {

        @SerializedName("category_id")
        private Long mCategoryId;
        @SerializedName("category_name")
        private String mCategoryName;
        @SerializedName("category_type")
        private String mCategoryType;

        public Long getCategoryId() {
            return mCategoryId;
        }

        public void setCategoryId(Long categoryId) {
            mCategoryId = categoryId;
        }

        public String getCategoryName() {
            return mCategoryName;
        }

        public void setCategoryName(String categoryName) {
            mCategoryName = categoryName;
        }

        public String getCategoryType() {
            return mCategoryType;
        }

        public void setCategoryType(String categoryType) {
            mCategoryType = categoryType;
        }

    }
    public class DaysDishes {


    }

    public class Error {


    }

}
