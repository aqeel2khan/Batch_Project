
package com.example.bottomanimationmydemo.meals.meal_purchase.model.meal_plan_subscribe;

import com.google.gson.annotations.SerializedName;

public class MealsSubscribedRespnse {

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

        @SerializedName("discount")
        private String mDiscount;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("end_date")
        private String mEndDate;
        @SerializedName("meal_id")
        private String mMealId;
        @SerializedName("payment_status")
        private String mPaymentStatus;
        @SerializedName("payment_type")
        private String mPaymentType;
        @SerializedName("start_date")
        private String mStartDate;
        @SerializedName("subtotal")
        private String mSubtotal;
        @SerializedName("tax")
        private String mTax;
        @SerializedName("total")
        private String mTotal;
        @SerializedName("transaction_id")
        private String mTransactionId;
        @SerializedName("user_id")
        private String mUserId;

        public String getDiscount() {
            return mDiscount;
        }

        public void setDiscount(String discount) {
            mDiscount = discount;
        }

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public String getEndDate() {
            return mEndDate;
        }

        public void setEndDate(String endDate) {
            mEndDate = endDate;
        }

        public String getMealId() {
            return mMealId;
        }

        public void setMealId(String mealId) {
            mMealId = mealId;
        }

        public String getPaymentStatus() {
            return mPaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            mPaymentStatus = paymentStatus;
        }

        public String getPaymentType() {
            return mPaymentType;
        }

        public void setPaymentType(String paymentType) {
            mPaymentType = paymentType;
        }

        public String getStartDate() {
            return mStartDate;
        }

        public void setStartDate(String startDate) {
            mStartDate = startDate;
        }

        public String getSubtotal() {
            return mSubtotal;
        }

        public void setSubtotal(String subtotal) {
            mSubtotal = subtotal;
        }

        public String getTax() {
            return mTax;
        }

        public void setTax(String tax) {
            mTax = tax;
        }

        public String getTotal() {
            return mTotal;
        }

        public void setTotal(String total) {
            mTotal = total;
        }

        public String getTransactionId() {
            return mTransactionId;
        }

        public void setTransactionId(String transactionId) {
            mTransactionId = transactionId;
        }

        public String getUserId() {
            return mUserId;
        }

        public void setUserId(String userId) {
            mUserId = userId;
        }

    }
    public class Error {


    }

}
