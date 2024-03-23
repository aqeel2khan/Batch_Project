
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class Batchgoal {

    @SerializedName("goal_name")
    private String mGoalName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("status")
    private Long mStatus;

    public String getGoalName() {
        return mGoalName;
    }

    public void setGoalName(String goalName) {
        mGoalName = goalName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
