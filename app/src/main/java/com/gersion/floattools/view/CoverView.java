package com.gersion.floattools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gersion.floattools.utils.ConvertUtils;
import com.gersion.floattools.utils.SpfUtils;

/**
 * @作者 a3266
 * @版本
 * @包名 com.wangxiandeng.floatball
 * @待完成
 * @创建时间 2016/11/30
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class CoverView extends View {

    private Paint mPaint;
    private int mAlpha;
    private int mRed;
    private int mGreen;
    private int mBlue;
    private int mCenterX;
    private int mCenterY;
    private Paint mTextPaint;
    private String mShowText = "";
    private int mTextSize;

    public CoverView(Context context) {
        this(context, null);
    }

    public CoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initCoverPaint();
        initTextPaint();
    }

    private void initTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
    }

    private void initCoverPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setARGB(mAlpha,mRed,mGreen,mBlue);
    }

    private void initData() {
        Context context = getContext();
        mRed = SpfUtils.getInt(context, "red");
        mGreen = SpfUtils.getInt(context, "green");
        mBlue = SpfUtils.getInt(context, "blue");
        mAlpha = SpfUtils.getInt(context, "alpha");

        mTextSize = ConvertUtils.sp2px(context, 18);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        canvas.drawRect(0, 0, width, height, mPaint);
        float start = getTextWidth(mTextPaint, mShowText);
        canvas.drawText(mShowText,mCenterX-start,mCenterY, mTextPaint);
    }

    //获取字符相对中心向右偏移的量
    private float getTextWidth(Paint paint, String text) {
        return paint.measureText(text, 0, text.length()) / 2;
    }

    public void setViewAlpha(int alpha) {
        mAlpha = alpha;
        mPaint.setAlpha(alpha);
        mShowText = "透明度："+mAlpha;
        mTextPaint.setColor(Color.WHITE);
        invalidate();
    }

    public void setRedColor(int red) {
        mRed = red;
        mPaint.setARGB(mAlpha, mRed, mGreen, mBlue);
        mShowText = "红色："+mRed;
        mTextPaint.setColor(Color.parseColor("#66ff0000"));
        invalidate();
    }

    public void setGreenColor(int green) {
        mGreen = green;
        mPaint.setARGB(mAlpha, mRed, mGreen, mBlue);
        mShowText = "绿色："+mGreen;
        mTextPaint.setColor(Color.parseColor("#6600ff00"));
        invalidate();
    }

    public void setBlueColor(int blue) {
        mBlue = blue;
        mPaint.setARGB(mAlpha, mRed, mGreen, mBlue);
        mShowText = "蓝色："+mBlue;
        mTextPaint.setColor(Color.parseColor("#660000ff"));
        invalidate();
    }

    public void saveRedColor() {
        saveValue("red", mRed);
    }

    public void saveGreenColor() {
        saveValue("green", mGreen);
    }

    public void saveBlueColor() {
        saveValue("blue", mBlue);
    }

    public void saveAlpha() {
        saveValue("alpha", mAlpha);
    }

    private void saveValue(String type, int value) {
        SpfUtils.putInt(getContext(), type, value);
        mShowText = "";
        invalidate();
    }
}
