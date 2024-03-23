
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class CourseLevel {

    @SerializedName("id")
    private Long mId;
    @SerializedName("level_name")
    private String mLevelName;
    @SerializedName("status")
    private Long mStatus;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getLevelName() {
        return mLevelName;
    }

    public void setLevelName(String levelName) {
        mLevelName = levelName;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
