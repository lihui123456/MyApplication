package com.example.lihui.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Ceated by LIHUI on 2017/2/21.
 */

public class MyTextView extends View {
    private static final String TAG = MyTextView.class.getSimpleName();
    /**
     * 文本
     */
    private String mText;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private int mTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;


    public MyTextView(Context context, AttributeSet attrs) {

        this(context, attrs,0);
    }

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.test);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.test_text:
                    mText = ta.getString(attr);
                    break;
                case R.styleable.test_testSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTextSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.test_textColor:
                    mTextColor = ta.getColor(attr, Color.BLACK);
                    break;
            }
        }
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mText =RandomText();
                postInvalidate();
            }
        });
        ta.recycle();
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);

    }
private String RandomText(){
    Random random = new Random();
    Set<Integer> set =new  HashSet<Integer>();
    while (set.size() < 4)
    {
        int randomInt = random.nextInt(10);
        set.add(randomInt);
    }
    StringBuffer sb = new StringBuffer();
    for (Integer i : set)
    {
        sb.append("" + i);
    }

    return sb.toString();
}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }



        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTextColor);
        canvas.drawText(mText, getWidth() / 2- mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

}
