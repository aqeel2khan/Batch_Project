
package com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.subscribe;

import com.google.gson.annotations.SerializedName;


public class CheckSubscribeModel {

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

        @SerializedName("subscribed")
        private String mSubscribed;

        public String getSubscribed() {
            return mSubscribed;
        }

        public void setSubscribed(String subscribed) {
            mSubscribed = subscribed;
        }

    }
    public class Error {


    }
}
