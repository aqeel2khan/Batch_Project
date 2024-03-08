package com.example.bottomanimationmydemo.model.meal_subscription_details_model;

import com.google.gson.annotations.SerializedName;

public class MealSubscriptionCategoryResponse {


        @SerializedName("category_id")
        private String mCategoryId;
        @SerializedName("category_name")
        private String mCategoryName;
        @SerializedName("category_type")
        private String mCategoryType;

       @SerializedName("isSelected")
       private Boolean misSelected=false;



        public String getCategoryId() {
            return mCategoryId;
        }

        public void setCategoryId(String categoryId) {
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

        public Boolean getSelected() {
        return misSelected;
    }

       public void setSelected(Boolean selected) {
           misSelected = selected;
    }



}
