
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class List {

    @SerializedName("course_detail")
    private CourseDetail mCourseDetail;
    @SerializedName("course_id")
    private Long mCourseId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("discount")
    private String mDiscount;
    @SerializedName("end_date")
    private String mEndDate;
    @SerializedName("id")
    private Long mId;
    @SerializedName("payment_status")
    private String mPaymentStatus;
    @SerializedName("payment_type")
    private String mPaymentType;
    @SerializedName("start_date")
    private String mStartDate;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("subtotal")
    private String mSubtotal;
    @SerializedName("tax")
    private String mTax;
    @SerializedName("today_workouts")
    private TodayWorkouts mTodayWorkouts;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("transaction_id")
    private String mTransactionId;
    @SerializedName("updated_at")
    private Object mUpdatedAt;
    @SerializedName("user_id")
    private Long mUserId;

    public CourseDetail getCourseDetail() {
        return mCourseDetail;
    }

    public void setCourseDetail(CourseDetail courseDetail) {
        mCourseDetail = courseDetail;
    }

    public Long getCourseId() {
        return mCourseId;
    }

    public void setCourseId(Long courseId) {
        mCourseId = courseId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDiscount() {
        return mDiscount;
    }

    public void setDiscount(String discount) {
        mDiscount = discount;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
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

    public TodayWorkouts getTodayWorkouts() {
        return mTodayWorkouts;
    }

    public void setTodayWorkouts(TodayWorkouts todayWorkouts) {
        mTodayWorkouts = todayWorkouts;
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

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

}
