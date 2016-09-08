package com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.iphoto.R;

/**
 * Created by duke on 16-9-7.
 */
public class CircularProgressView extends View {
    private static final int INNER_CIRCLE_COLOR = Color.GRAY;
    private static final int PROGRESS_BAR_COLOR = Color.RED;
    private static final int TEXT_DEFAULT_COLOR = Color.WHITE;

    private static final int INNER_CIRCLE_RADIUS = 60;
    private static final int PROGRESS_BAR_WIDTH = 12;
    private static final int INVALID_TEXT_SIZE = -1;

    private int mInnerCircleColor = INNER_CIRCLE_COLOR;
    private int mInnerCircleRadius = INNER_CIRCLE_RADIUS;
    private Paint mInnerCirclePaint;

    private int mProgressBarColor = PROGRESS_BAR_COLOR;
    private float mProgressBarWidth = PROGRESS_BAR_WIDTH;
    private Paint mProgressBarPaint;

    private int mTextColor = TEXT_DEFAULT_COLOR;
    private int mTextSizePixel = INVALID_TEXT_SIZE;
    private Paint mTextPaint;

    private float mProgressBarRadius;
    private int mXCenter;
    private int mYCenter;
    private float mTextWidth;
    private float mTextHeight;
    private int mTotalProgress = 100;
    private int mProgress;

    private static int mTest;

    public CircularProgressView(Context context) {
        super(context);
        init();
        setParam();
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(context, attrs);
        setParam();
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        mTextSizePixel = INNER_CIRCLE_RADIUS / 2;

        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setStyle(Paint.Style.FILL);

        mProgressBarPaint = new Paint();
        mProgressBarPaint.setAntiAlias(true);
        mProgressBarPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CircularProgressView, 0, 0);

        mInnerCircleColor = typeArray.getColor(R.styleable.CircularProgressView_circleColor, INNER_CIRCLE_COLOR);
        mProgressBarColor = typeArray.getColor(R.styleable.CircularProgressView_ringColor, PROGRESS_BAR_COLOR);
        mTextColor = typeArray.getColor(R.styleable.CircularProgressView_textColor, TEXT_DEFAULT_COLOR);

        mInnerCircleRadius = typeArray.getDimensionPixelOffset(R.styleable.CircularProgressView_radius, INNER_CIRCLE_RADIUS);
        mProgressBarWidth = typeArray.getDimension(R.styleable.CircularProgressView_strokeWidth, PROGRESS_BAR_WIDTH);
        mTextSizePixel = typeArray.getDimensionPixelOffset(R.styleable.CircularProgressView_textSize, INVALID_TEXT_SIZE);

        if (INVALID_TEXT_SIZE == mTextSizePixel) {
            mTextSizePixel = mInnerCircleRadius / 2;
        }

        mProgressBarRadius = mInnerCircleRadius + mProgressBarWidth / 2;
    }

    private void setParam() {
        mInnerCirclePaint.setColor(mInnerCircleColor);

        mProgressBarPaint.setColor(mProgressBarColor);
        mProgressBarPaint.setStrokeWidth(mProgressBarWidth);

        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSizePixel);

        FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        if (mProgressBarRadius > mXCenter || mProgressBarRadius > mYCenter) {
            throw new IllegalArgumentException("The circular progress bar radius is greater then view size.");
        }

        canvas.drawCircle(mXCenter, mYCenter, mInnerCircleRadius, mInnerCirclePaint);

        if (mProgress > 0 ) {
            RectF oval = new RectF();
            oval.left = mXCenter - mProgressBarRadius;
            oval.top = mYCenter - mProgressBarRadius;
            oval.right = mXCenter + mProgressBarRadius;
            oval.bottom = mYCenter + mProgressBarRadius;
            canvas.drawArc(oval, -90, ((float)mProgress / mTotalProgress) * 360, false, mProgressBarPaint);

            String progressText = mProgress + "%";
            mTextWidth = mTextPaint.measureText(progressText, 0, progressText.length());
            canvas.drawText(progressText, mXCenter - mTextWidth / 2, mYCenter + mTextHeight / 4, mTextPaint);
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }
}
