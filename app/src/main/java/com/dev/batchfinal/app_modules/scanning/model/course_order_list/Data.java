
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("list")
    private java.util.List<com.dev.batchfinal.app_modules.scanning.model.course_order_list.List> mList;

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public java.util.List<com.dev.batchfinal.app_modules.scanning.model.course_order_list.List> getList() {
        return mList;
    }

    public void setList(java.util.List<com.dev.batchfinal.app_modules.scanning.model.course_order_list.List> list) {
        mList = list;
    }

}
