
package com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_drop_off;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DeliveryDropOffResponse {

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

        @SerializedName("id")
        private Long mId;
        @SerializedName("options")
        private String mOptions;

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getOptions() {
            return mOptions;
        }

        public void setOptions(String options) {
            mOptions = options;
        }

    }

    public class Error {


    }

}
