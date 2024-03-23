
package com.dev.batchfinal.app_modules.scanning.model.course_order_list;

import com.google.gson.annotations.SerializedName;


public class VideoDetail {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("folder_id")
    private Long mFolderId;
    @SerializedName("height")
    private String mHeight;
    @SerializedName("id")
    private Long mId;
    @SerializedName("player_embed_url")
    private String mPlayerEmbedUrl;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("video_description")
    private String mVideoDescription;
    @SerializedName("video_id")
    private String mVideoId;
    @SerializedName("video_title")
    private String mVideoTitle;
    @SerializedName("width")
    private String mWidth;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public Long getFolderId() {
        return mFolderId;
    }

    public void setFolderId(Long folderId) {
        mFolderId = folderId;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPlayerEmbedUrl() {
        return mPlayerEmbedUrl;
    }

    public void setPlayerEmbedUrl(String playerEmbedUrl) {
        mPlayerEmbedUrl = playerEmbedUrl;
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

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getVideoDescription() {
        return mVideoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        mVideoDescription = videoDescription;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public String getVideoTitle() {
        return mVideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        mVideoTitle = videoTitle;
    }

    public String getWidth() {
        return mWidth;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

}
