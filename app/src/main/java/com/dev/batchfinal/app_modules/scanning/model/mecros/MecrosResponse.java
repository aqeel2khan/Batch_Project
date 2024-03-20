
package com.dev.batchfinal.app_modules.scanning.model.mecros;

import com.google.gson.annotations.SerializedName;


public class MecrosResponse {

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

        @SerializedName("calories")
        private String mCalories;
        @SerializedName("carbs")
        private Double mCarbs;
        @SerializedName("fat")
        private Double mFat;
        @SerializedName("protein")
        private Double mProtein;

        public String getCalories() {
            return mCalories;
        }

        public void setCalories(String calories) {
            mCalories = calories;
        }

        public Double getCarbs() {
            return mCarbs;
        }

        public void setCarbs(Double carbs) {
            mCarbs = carbs;
        }

        public Double getFat() {
            return mFat;
        }

        public void setFat(Double fat) {
            mFat = fat;
        }

        public Double getProtein() {
            return mProtein;
        }

        public void setProtein(Double protein) {
            mProtein = protein;
        }

    }
    public class Error {


    }

}
