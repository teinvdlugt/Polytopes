package com.teinvdlugt.android.polytopes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class VerticalScroller extends View {
    private Paint backgroundPaint, circlePaint;
    private double speed = 5;
    private double progress;
    private OnProgressChangeListener onProgressChangeListener;
    private int touchY = -1;
    private int prevTouchY = -1;
    private RectF background;
    private float backgroundRadius;

    public interface OnProgressChangeListener {
        void onProgressChanged(double progress);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(background, backgroundRadius, backgroundRadius, backgroundPaint);
        if (touchY != -1)
            canvas.drawCircle(getWidth() / 2, touchY, getWidth() * .5f, circlePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new RectF(0, 0, getWidth(), getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchY = (int) event.getY();
            prevTouchY = touchY;
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touchY = (int) event.getY();
            progress += (1. * prevTouchY - event.getY()) / getHeight() * speed;
            prevTouchY = touchY;
            invalidate();
            if (onProgressChangeListener != null)
                onProgressChangeListener.onProgressChanged(progress);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            touchY = -1;
            invalidate();
            return true;
        }
        return false;
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.argb(0x22, 0, 0, 0));
        circlePaint = new Paint(backgroundPaint);
        backgroundRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                getResources().getDisplayMetrics());
    }

    public VerticalScroller(Context context) {
        super(context);
        init();
    }

    public VerticalScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
