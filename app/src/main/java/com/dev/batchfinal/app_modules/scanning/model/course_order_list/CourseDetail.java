
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class CourseDetail {

    @SerializedName("coach_detail")
    private CoachDetail mCoachDetail;
    @SerializedName("course_duration")
    private List<CourseDuration> mCourseDuration;
    @SerializedName("course_id")
    private Long mCourseId;
    @SerializedName("course_image")
    private String mCourseImage;
    @SerializedName("course_level")
    private CourseLevel mCourseLevel;
    @SerializedName("course_name")
    private String mCourseName;
    @SerializedName("course_price")
    private String mCoursePrice;
    @SerializedName("course_promo_video")
    private Object mCoursePromoVideo;
    @SerializedName("course_repetition")
    private String mCourseRepetition;
    @SerializedName("course_validity")
    private String mCourseValidity;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("discount_price")
    private Object mDiscountPrice;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("goals")
    private List<Goal> mGoals;
    @SerializedName("per_day_workout")
    private String mPerDayWorkout;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("weight_required")
    private String mWeightRequired;
    @SerializedName("workout_type")
    private List<WorkoutType> mWorkoutType;

    public CoachDetail getCoachDetail() {
        return mCoachDetail;
    }

    public void setCoachDetail(CoachDetail coachDetail) {
        mCoachDetail = coachDetail;
    }

    public List<CourseDuration> getCourseDuration() {
        return mCourseDuration;
    }

    public void setCourseDuration(List<CourseDuration> courseDuration) {
        mCourseDuration = courseDuration;
    }

    public Long getCourseId() {
        return mCourseId;
    }

    public void setCourseId(Long courseId) {
        mCourseId = courseId;
    }

    public String getCourseImage() {
        return mCourseImage;
    }

    public void setCourseImage(String courseImage) {
        mCourseImage = courseImage;
    }

    public CourseLevel getCourseLevel() {
        return mCourseLevel;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        mCourseLevel = courseLevel;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getCoursePrice() {
        return mCoursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        mCoursePrice = coursePrice;
    }

    public Object getCoursePromoVideo() {
        return mCoursePromoVideo;
    }

    public void setCoursePromoVideo(Object coursePromoVideo) {
        mCoursePromoVideo = coursePromoVideo;
    }

    public String getCourseRepetition() {
        return mCourseRepetition;
    }

    public void setCourseRepetition(String courseRepetition) {
        mCourseRepetition = courseRepetition;
    }

    public String getCourseValidity() {
        return mCourseValidity;
    }

    public void setCourseValidity(String courseValidity) {
        mCourseValidity = courseValidity;
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

    public Object getDiscountPrice() {
        return mDiscountPrice;
    }

    public void setDiscountPrice(Object discountPrice) {
        mDiscountPrice = discountPrice;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public List<Goal> getGoals() {
        return mGoals;
    }

    public void setGoals(List<Goal> goals) {
        mGoals = goals;
    }

    public String getPerDayWorkout() {
        return mPerDayWorkout;
    }

    public void setPerDayWorkout(String perDayWorkout) {
        mPerDayWorkout = perDayWorkout;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
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

    public String getWeightRequired() {
        return mWeightRequired;
    }

    public void setWeightRequired(String weightRequired) {
        mWeightRequired = weightRequired;
    }

    public List<WorkoutType> getWorkoutType() {
        return mWorkoutType;
    }

    public void setWorkoutType(List<WorkoutType> workoutType) {
        mWorkoutType = workoutType;
    }

}
