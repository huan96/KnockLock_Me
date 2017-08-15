package com.zergitas.teamb.myapplication.until;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Binh NK on 3/31/2017.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewCustom extends TextView {
    public TextViewCustom(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("SanFranciscoBold.ttf", context);
        setTypeface(customFont);
    }
}
