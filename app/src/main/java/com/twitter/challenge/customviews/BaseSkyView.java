package com.twitter.challenge.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.twitter.challenge.R;

/**
 * Created by sudavid on 3/3/18.
 */

public class BaseSkyView extends View {

    protected boolean isStatic =false;
    protected boolean isAnimated = false;
    protected int strokeColor = Color.BLACK;
    protected int bgColor = Color.TRANSPARENT;

    public BaseSkyView(Context context) {
        super(context);
    }

    public BaseSkyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        extractAttributes(context,attrs);
    }

    private void extractAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.custom_view);

        // get attributes from layout
        isStatic = array.getBoolean(R.styleable.custom_view_isStatic, this.isStatic);
        strokeColor = array.getColor(R.styleable.custom_view_strokeColor, this.strokeColor);
        bgColor = array.getColor(R.styleable.custom_view_bgColor, this.bgColor);

        if(strokeColor == 0){
            strokeColor = Color.BLACK;
        }

        if(bgColor == 0) {
            bgColor = Color.TRANSPARENT;
        }
        array.recycle();
    }


    public boolean isStatic() {
        return isStatic;
    }

    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setIsAnimated(boolean isAnimated) {
        this.isAnimated = isAnimated;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
