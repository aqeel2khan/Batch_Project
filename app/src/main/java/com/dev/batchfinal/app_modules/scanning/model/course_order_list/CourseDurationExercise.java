
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class CourseDurationExercise {

    @SerializedName("course_duration_exercise_id")
    private Long mCourseDurationExerciseId;
    @SerializedName("course_duration_id")
    private Long mCourseDurationId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("exercise_set")
    private String mExerciseSet;
    @SerializedName("exercise_time")
    private String mExerciseTime;
    @SerializedName("exercise_wraps")
    private Object mExerciseWraps;
    @SerializedName("instruction")
    private String mInstruction;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("video_detail")
    private VideoDetail mVideoDetail;
    @SerializedName("video_id")
    private Long mVideoId;
    @SerializedName("workout_status")
    private Object mWorkoutStatus;

    public Long getCourseDurationExerciseId() {
        return mCourseDurationExerciseId;
    }

    public void setCourseDurationExerciseId(Long courseDurationExerciseId) {
        mCourseDurationExerciseId = courseDurationExerciseId;
    }

    public Long getCourseDurationId() {
        return mCourseDurationId;
    }

    public void setCourseDurationId(Long courseDurationId) {
        mCourseDurationId = courseDurationId;
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

    public String getExerciseSet() {
        return mExerciseSet;
    }

    public void setExerciseSet(String exerciseSet) {
        mExerciseSet = exerciseSet;
    }

    public String getExerciseTime() {
        return mExerciseTime;
    }

    public void setExerciseTime(String exerciseTime) {
        mExerciseTime = exerciseTime;
    }

    public Object getExerciseWraps() {
        return mExerciseWraps;
    }

    public void setExerciseWraps(Object exerciseWraps) {
        mExerciseWraps = exerciseWraps;
    }

    public String getInstruction() {
        return mInstruction;
    }

    public void setInstruction(String instruction) {
        mInstruction = instruction;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public VideoDetail getVideoDetail() {
        return mVideoDetail;
    }

    public void setVideoDetail(VideoDetail videoDetail) {
        mVideoDetail = videoDetail;
    }

    public Long getVideoId() {
        return mVideoId;
    }

    public void setVideoId(Long videoId) {
        mVideoId = videoId;
    }

    public Object getWorkoutStatus() {
        return mWorkoutStatus;
    }

    public void setWorkoutStatus(Object workoutStatus) {
        mWorkoutStatus = workoutStatus;
    }

}
