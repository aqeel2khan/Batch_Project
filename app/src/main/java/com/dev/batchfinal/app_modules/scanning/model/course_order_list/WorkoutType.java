
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class WorkoutType {

    @SerializedName("course_id")
    private Long mCourseId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("workout_type_id")
    private Long mWorkoutTypeId;
    @SerializedName("workoutdetail")
    private Workoutdetail mWorkoutdetail;

    public Long getCourseId() {
        return mCourseId;
    }

    public void setCourseId(Long courseId) {
        mCourseId = courseId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getWorkoutTypeId() {
        return mWorkoutTypeId;
    }

    public void setWorkoutTypeId(Long workoutTypeId) {
        mWorkoutTypeId = workoutTypeId;
    }

    public Workoutdetail getWorkoutdetail() {
        return mWorkoutdetail;
    }

    public void setWorkoutdetail(Workoutdetail workoutdetail) {
        mWorkoutdetail = workoutdetail;
    }

}
