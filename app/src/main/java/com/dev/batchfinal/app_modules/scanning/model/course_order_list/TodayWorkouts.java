
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class TodayWorkouts {

    @SerializedName("calorie_burn")
    private String mCalorieBurn;
    @SerializedName("course_duration_exercise")
    private List<CourseDurationExercise> mCourseDurationExercise;
    @SerializedName("course_duration_id")
    private Long mCourseDurationId;
    @SerializedName("course_id")
    private Long mCourseId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("day_name")
    private String mDayName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("no_of_exercise")
    private Long mNoOfExercise;
    @SerializedName("Row")
    private Long mRow;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("workout_time")
    private String mWorkoutTime;

    public String getCalorieBurn() {
        return mCalorieBurn;
    }

    public void setCalorieBurn(String calorieBurn) {
        mCalorieBurn = calorieBurn;
    }

    public List<CourseDurationExercise> getCourseDurationExercise() {
        return mCourseDurationExercise;
    }

    public void setCourseDurationExercise(List<CourseDurationExercise> courseDurationExercise) {
        mCourseDurationExercise = courseDurationExercise;
    }

    public Long getCourseDurationId() {
        return mCourseDurationId;
    }

    public void setCourseDurationId(Long courseDurationId) {
        mCourseDurationId = courseDurationId;
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

    public String getDayName() {
        return mDayName;
    }

    public void setDayName(String dayName) {
        mDayName = dayName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Long getNoOfExercise() {
        return mNoOfExercise;
    }

    public void setNoOfExercise(Long noOfExercise) {
        mNoOfExercise = noOfExercise;
    }

    public Long getRow() {
        return mRow;
    }

    public void setRow(Long row) {
        mRow = row;
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

    public String getWorkoutTime() {
        return mWorkoutTime;
    }

    public void setWorkoutTime(String workoutTime) {
        mWorkoutTime = workoutTime;
    }

}
