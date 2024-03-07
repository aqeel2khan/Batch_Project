
package com.dev.batchfinal.subscribe_list_model;

import com.google.gson.annotations.SerializedName;


public class MealSubscribeListRequest {

    @SerializedName("user_id")
    private String mUserId;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
