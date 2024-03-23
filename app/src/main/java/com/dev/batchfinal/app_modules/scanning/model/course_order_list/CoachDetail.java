
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class CoachDetail {

    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_bio")
    private Object mProfileBio;
    @SerializedName("profile_photo_path")
    private String mProfilePhotoPath;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getProfileBio() {
        return mProfileBio;
    }

    public void setProfileBio(Object profileBio) {
        mProfileBio = profileBio;
    }

    public String getProfilePhotoPath() {
        return mProfilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        mProfilePhotoPath = profilePhotoPath;
    }

}
