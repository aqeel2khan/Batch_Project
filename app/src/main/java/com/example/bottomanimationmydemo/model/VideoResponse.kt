package com.example.bottomanimationmydemo.model

import com.google.gson.annotations.SerializedName




class VideoResponse {

    @SerializedName("files")
    private val videoFiles: List<VideoFile>? = null

    // Getter for videoFiles

    // Getter for videoFiles
    // Inner class representing a video file
    class VideoFile {
        @SerializedName("link")
        private val videoUrl: String? = null // Getter for videoUrl
    }

}