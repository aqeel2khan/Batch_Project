package com.dev.batchfinal.app_custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.dev.batchfinal.R
import com.dev.batchfinal.app_utils.FontCache


class MyCustomButton : AppCompatButton {
    private var ctx: Context? = null

    constructor(context: Context) : super(context) {
        this.ctx = context
        applyCustomFont(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.ctx = context
        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.ctx = context
        applyCustomFont(context, attrs)
    }

    private fun applyCustomFont(context: Context, attrs: AttributeSet?) {

        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        val fontName = attributeArray.getString(R.styleable.CustomView_fontcustom)

        val textStyle = attrs!!.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        val customFont = selectTypeface(context, fontName, textStyle)
        typeface = customFont

        attributeArray.recycle()
    }


    private fun selectTypeface(context: Context, fontName: String?, textStyle: Int): Typeface? {
        return if (fontName == null) {
            FontCache.getTypeface("outfit_medium.ttf", context)
        } else if (fontName.contentEquals("outfit_medium.ttf")) {
            FontCache.getTypeface("outfit_medium.ttf", context)

        } else {
            FontCache.getTypeface("outfit_semi_old.ttf", context)
        }
    }

    companion object {

        const val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
    }

}
