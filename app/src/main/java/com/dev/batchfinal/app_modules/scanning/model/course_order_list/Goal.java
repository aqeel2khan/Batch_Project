
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Goal {

    @SerializedName("batchgoal")
    private List<Batchgoal> mBatchgoal;
    @SerializedName("course_id")
    private Long mCourseId;
    @SerializedName("goal_id")
    private Long mGoalId;
    @SerializedName("id")
    private Long mId;

    public List<Batchgoal> getBatchgoal() {
        return mBatchgoal;
    }

    public void setBatchgoal(List<Batchgoal> batchgoal) {
        mBatchgoal = batchgoal;
    }

    public Long getCourseId() {
        return mCourseId;
    }

    public void setCourseId(Long courseId) {
        mCourseId = courseId;
    }

    public Long getGoalId() {
        return mGoalId;
    }

    public void setGoalId(Long goalId) {
        mGoalId = goalId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

}
