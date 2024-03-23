
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class Workoutdetail {

    @SerializedName("id")
    private Long mId;
    @SerializedName("workout_type")
    private String mWorkoutType;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getWorkoutType() {
        return mWorkoutType;
    }

    public void setWorkoutType(String workoutType) {
        mWorkoutType = workoutType;
    }

}
