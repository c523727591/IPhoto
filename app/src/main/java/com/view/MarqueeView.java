package com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.util.ILog;

import java.lang.ref.WeakReference;

public class MarqueeView extends ImageView {
    private static final int MSG_WHAT_UPDATE_MARQUEE = 0;

    public static final int MARQUEE_NULL = 0;        // 0: fixed
    public static final int MARQUEE_FROM_LEFT = 1;   // 1: from left to right
    public static final int MARQUEE_FROM_RIGHT = 2;  // 2: from right to left

    private Context mContext = null;
    private MarqueeHandler mHandler = null;

    private String mTextString;
    private int mTextSize;
    private int mTextColor;

    private int mNewMarqueeType;
    private int mMarqueeType;    // marquee type
    private int mFrameInterval;  // 单位：毫秒，每隔多少毫秒移动一次
    private int mMoveStep;       // 单位：像素，每次移动多少像素
    private int mLoopCount;      // value: -1 or a number > 0. (-1: scroll always)
    private int mLoopInterval;   // 循环滚动一次之后，暂停滚动的时间间隔

    private boolean mMoveAlways;
    private int mGaps;
    private Paint.Align mTextAlign;

    private volatile boolean mRefreshing;  // marquee running
    private int mScrollX;
    private int mDrawX;
    private int mDrawY;
    private int mMaxScroll;
    private int mTextWidth;
    private Paint mpPaint;

    public MarqueeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mHandler = new MarqueeHandler(this);

        mTextString = "";
        mTextSize = 23;
        mTextColor = Color.WHITE;

        mNewMarqueeType = MARQUEE_FROM_LEFT;  // MARQUEE_FROM_RIGHT
        mMarqueeType = MARQUEE_NULL;
        mFrameInterval = 100;
        mMoveStep = mTextSize / 10;
        mLoopCount = -1;
        mLoopInterval = 0;

        mMoveAlways = true;
        mGaps = 0;
        mTextAlign = Paint.Align.LEFT;

        mRefreshing = false;
        mScrollX = 0;
        mDrawX = 0;
        mDrawY = 0;
        mMaxScroll = 0;
        mTextWidth = 0;
        mpPaint = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        String movedText = mTextString;

        if (mTextAlign == Paint.Align.RIGHT) {
            mDrawX = width - getPaddingRight() - mTextSize * 2 / 3;
        } else if (mTextAlign == Paint.Align.LEFT) {
            mDrawX = getPaddingLeft();
        } else if (mTextAlign == Paint.Align.CENTER) {
            mDrawX = width / 2;
        }

        if (mMarqueeType == MARQUEE_FROM_RIGHT) {
            // do nothing
        } else if (mMarqueeType == MARQUEE_FROM_LEFT) {
            // movedText = reverseString(mTextString);
        }

        mpPaint.setTextAlign(mTextAlign);

        int tarx = mDrawX + mScrollX;
        int tary = mDrawY + mTextSize + getPaddingTop() - 3;

        canvas.save();
        canvas.clipRect(getPaddingLeft(), 0, width - getPaddingRight(), getHeight());
        canvas.drawText(movedText, tarx, tary, mpPaint);
        int tailx = -1;

        if (mTextWidth > width) {
            if (mMarqueeType == MARQUEE_FROM_LEFT) {
                tailx = tarx - mMaxScroll;
            } else if (mMarqueeType == MARQUEE_FROM_RIGHT) {
                tailx = tarx + mMaxScroll;
            }
            if (mScrollX != 0) {
                canvas.drawText(movedText, tailx, tary, mpPaint);
            }
        } else {
            if (mMarqueeType == MARQUEE_FROM_LEFT) {
                tailx = tarx - mGaps;
            } else if (mMarqueeType == MARQUEE_FROM_RIGHT) {
                tailx = tarx + mGaps;
            }
            canvas.drawText(movedText, tailx, tary, mpPaint);
        }
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        checkMarquee();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode =  MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureTextSize();

        int targetWidth = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            ILog.d("With mode is MeasureSpec.EXACTLY");
            targetWidth = widthSize;
        } else {
            throw new IllegalArgumentException("MarqueeView layout_width and layout_height don't use wrap_content");
            /* TODO
            int parentWidth = getParent().getWidth();
            int spaceH = getPaddingLeft() + getPaddingRight();
            if ((mTextWidth + spaceH) > parentWidth) {
                targetWidth = parentWidth;
            } else {
                targetWidth = (mTextWidth + spaceH);
            }
            */
        }

        int targetHeight = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            ILog.d("Height mode is MeasureSpec.EXACTLY");
            targetHeight = heightSize;
        } else {
            throw new IllegalArgumentException("MarqueeView layout_width and layout_height don't use wrap_content");
            /* TODO
            int parentHeight = getParent().getHeight();
            int spaceV = getPaddingTop() + getPaddingBottom();
            targetHeight = mTextSize + spaceV;
            */
        }

        setMeasuredDimension(targetWidth, targetHeight);
    }

    public void setText(String text) {
        mTextString = text;
        requestLayout();
    }

    public void setTextSize(int size) {
        mTextSize = size;
        requestLayout();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        requestLayout();
    }

    // set the direction for the marquee.
    public void setMarqueeType(int type) {
        if (type != mNewMarqueeType) {
            mNewMarqueeType = type;
            invalidate();
            requestLayout();
        } else {
            return;
        }
    }

    public void setFrameInterval(int millisecond) {
        mFrameInterval = millisecond;
    }

    public void setSteps(int steps) {
        mMoveStep = steps;
    }

    public void setLoopCounts(int loopCount) {
        mLoopCount = loopCount;
    }

    public void setLoopInterval(int millisecond) {
        mLoopInterval = millisecond;
    }

    public void setMarqueeMove(boolean always) {
        mMoveAlways = always;
    }

    public void setGaps(int gap) {
        if (gap > 0 && mGaps != gap) {
            mGaps = gap;
        }
    }

    public void setTextAlign(Paint.Align align) {
        mTextAlign = align;
    }

    private String reverseString(String source) {
        if (TextUtils.isEmpty(source)) {
            ILog.e("reverse string source is null or empty.");
            return source;
        }

        int size = source.toCharArray().length;
        int pb = 0;
        int pe = size - 1;
        char[] dest = new char[size];
        while (pe >= 0) {
            char tem = source.charAt(pe);
            dest[pb] = tem;
            pe -= 1;
            pb += 1;
        }
        return new String(dest);
    }

    private void measureTextSize() {
        if (null == mpPaint) {
            mpPaint = new Paint();
        }

        mpPaint.setTextSize(mTextSize);
        mpPaint.setColor(mTextColor);

        if (!mTextString.isEmpty()) {
            mTextWidth = (int)mpPaint.measureText(mTextString);
        }
    }

    private void checkMarquee() {
        int vw = getWidth() - getPaddingLeft() - getPaddingRight();
        if (vw <= 0) {
            ILog.e("checkMarquee:width=" + getWidth() + ",paddingLeft=" + getPaddingLeft() + ",paddingRight=" + getPaddingRight());
            return;
        }

        if (mNewMarqueeType != MARQUEE_NULL) {
            mMarqueeType = mNewMarqueeType;

            if (mTextWidth > vw) {
                mGaps = vw / 2;
                mMaxScroll = mTextWidth + mGaps;
            } else if (mMoveAlways) {
                if (mTextAlign == Paint.Align.LEFT) {
                    if (mMarqueeType == MARQUEE_FROM_LEFT) {
                        mMaxScroll = vw;
                        mGaps = vw;
                    } else if (mMarqueeType == MARQUEE_FROM_RIGHT) {
                        mMaxScroll = mTextWidth;
                        mGaps = vw;
                    }
                } else if (mTextAlign == Paint.Align.RIGHT) {
                    if (mMarqueeType == MARQUEE_FROM_RIGHT) {
                        mGaps = vw;
                        mMaxScroll = vw;
                    } else if (mMarqueeType == MARQUEE_FROM_LEFT) {
                        mMaxScroll = mTextWidth;
                        mGaps = vw;
                    }
                }
            } else {
                ILog.e("checkMarquee: mTextWidth = " + mTextWidth + ", vw = " + vw + ", mMoveAlways = " + mMoveAlways);
                return;
            }

            if (mLoopCount == -1 || mLoopCount > 0) {
                mHandler.removeMessages(MSG_WHAT_UPDATE_MARQUEE);
                startToShowMarquee();
            }
        }
    }

    private void startToShowMarquee() {
        mRefreshing = true;
        boolean mEnd = false;
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        if (mMarqueeType == MARQUEE_FROM_RIGHT) {
            mScrollX -= mMoveStep;

            if (mScrollX <= -mMaxScroll) {
                if (mTextWidth > width) {
                    mScrollX = 0;
                }
                if (mTextWidth <= width) {
                    mScrollX = mScrollX + mGaps;
                }
                if (mLoopInterval > 0) {
                    mEnd = true;
                }
                if (mLoopCount > 0) {
                    mLoopCount -= 1;
                }
                if (mLoopCount == 0) {
                    stopToShowMarquee();
                }
            }
        } else if (mMarqueeType == MARQUEE_FROM_LEFT) {
            mScrollX += mMoveStep;
            if (mScrollX >= mMaxScroll) {
                if (mTextWidth > width) {
                    mScrollX = 0;
                }
                if (mTextWidth <= width) {
                    mScrollX = mScrollX - mGaps;
                }
                if (mLoopInterval > 0) {
                    mEnd = true;
                }
                if (mLoopCount > 0) {
                    mLoopCount -= 1;
                }
                if (mLoopCount == 0) {
                    stopToShowMarquee();
                }
            }
        }

        invalidate();

        if (!mRefreshing) {
            mScrollX = 0;
            return;
        }

        Message msg = Message.obtain();
        msg.what = MSG_WHAT_UPDATE_MARQUEE;
        if (mEnd) {
            mHandler.removeMessages(MSG_WHAT_UPDATE_MARQUEE);
            mHandler.sendMessageDelayed(msg, mLoopInterval);
        } else {
            mHandler.sendMessageDelayed(msg, mFrameInterval);
        }
    }

    private void stopToShowMarquee() {
        mRefreshing = false;
        mHandler.removeMessages(MSG_WHAT_UPDATE_MARQUEE);
    }

    private static class MarqueeHandler extends Handler {
        private WeakReference<MarqueeView> mWRMarqueeView = null;

        public MarqueeHandler(MarqueeView marqueeView) {
            super(Looper.getMainLooper());
            mWRMarqueeView = new WeakReference<MarqueeView>(marqueeView);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_WHAT_UPDATE_MARQUEE) {
                MarqueeView marqueeView = mWRMarqueeView.get();
                if (null != marqueeView) {
                    if (marqueeView.mRefreshing) {
                        marqueeView.startToShowMarquee();
                    } else {
                        marqueeView.stopToShowMarquee();
                    }
                }
            }
        }
    }

    public void stopUpdate() {
        stopToShowMarquee();
    }
}