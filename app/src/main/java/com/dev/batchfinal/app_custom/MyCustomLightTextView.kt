package com.dev.batchfinal.app_custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.dev.batchfinal.R
import com.dev.batchfinal.app_utils.FontCache

class MyCustomLightTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        applyCustomFont(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyCustomFont(context, attrs)
    }


    private fun applyCustomFont(context: Context, attrs: AttributeSet?) {
        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        val fontName = attributeArray.getString(R.styleable.CustomView_fontcustom)

        val textStyle = attrs!!.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.BOLD)

        val customFont = setTypeface(context, fontName, textStyle)
        typeface = customFont
        attributeArray.recycle()
    }


    private fun setTypeface(context: Context, fontName: String?, textStyle: Int): Typeface? {
        return if (fontName == null) {
            FontCache.getTypeface("outfit_light.ttf", context)
        } else if (fontName.contentEquals("outfit_light.ttf")) {
            FontCache.getTypeface("outfit_light", context)
        } else {
            FontCache.getTypeface("outfit_regular.ttf", context)
        }
    }

    companion object {

        val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
    }

}

