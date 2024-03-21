package com.dev.batchfinal.app_modules.account.minterface;

import android.graphics.Bitmap;

import java.io.File;

public interface ImageResizeCallback {
    void onSuccess(String base64String, Bitmap bitmap, File file);
    void onFailure(String msg);
}
