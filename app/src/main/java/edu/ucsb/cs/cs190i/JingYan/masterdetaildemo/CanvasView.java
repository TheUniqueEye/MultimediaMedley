package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



/**
 * Created by EYE on 29/04/2017.
 */

public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
    private Canvas canvas;
    private SurfaceHolder holder;
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private float mScaleFactor = 1.f;
    float mRotationDegrees = 0.f;
    float mFocusX = 0.f;
    float mFocusY = 0.f;
    Matrix mMatrix = new Matrix();
    private Bitmap bmp;

    CanvasView cv;
    private int AXIS_X_MIN = 0,AXIS_Y_MIN=0,AXIS_X_MAX=100,AXIS_Y_MAX=100;
    private RectF mCurrentViewport =
            new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);

    float lastXAxis=0f,lastYAxis=0f;
    float lastXAxis2=0f,lastYAxis2=0f;
    float xAxis = 0f;
    float yAxis = 0f;
    double rotateDegree = 0f;


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }


    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        //mRotateDetector = new RotateGestureDetector(context, new RotateListener());
        //mMoveDetector = new MoveGestureDetector(context, new MoveListener());
        mGestureDetector = new GestureDetector(context, new PanListener());
        setWillNotDraw(false);
        cv = this;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setImageBitmap(Bitmap bitmap) {
        //Log.i("bitmap", "" + bitmap);
        //imgCanvas = new Canvas();
        bmp = bitmap;
        canvas = holder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // set background to transparent
        canvas.drawBitmap(bitmap, 0, 0, null);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);
        //mRotateDetector.onTouchEvent(ev);
        //mMoveDetector.onTouchEvent(ev);
        mGestureDetector.onTouchEvent(ev);

        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                //Log.d("ACTION_DOWN","ACTION_DOWN");
                final float x = ev.getX();
                final float y = ev.getY();
                lastXAxis = x;
                lastYAxis = y;
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                //Log.d("ACTION_DOWN","ACTION_DOWN");
                final float x2 = ev.getX();
                final float y2 = ev.getY();
                lastXAxis2 = x2;
                lastYAxis2 = y2;

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //Log.d("ACTION_MOVE","ACTION_MOVE");
                final float x = ev.getX();
                final float y = ev.getY();
                final float dx = x - lastXAxis;
                final float dy = y - lastYAxis;
                xAxis += dx;
                yAxis += dy;
                rotateDegree = Math.atan((lastYAxis2-lastYAxis)/(lastXAxis2-lastXAxis));
                cv.setTranslationX(xAxis);
                cv.setTranslationY(yAxis);

                if(lastYAxis2!=0 || lastXAxis2!=0){
                    //cv.setRotationX(lastXAxis2-lastXAxis);
                    //cv.setRotationY(lastYAxis2-lastYAxis);
                    Log.d("setRotationX",""+(lastXAxis2-lastXAxis));
                    Log.d("setRotationY",""+(lastYAxis2-lastYAxis));
                }

                break;

            }
            case MotionEvent.ACTION_UP: {
                //Log.d("ACTION_UP","ACTION_UP");
                break;
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas1) {
        super.onDraw(canvas1);
        canvas1.save();
        canvas1.scale(mScaleFactor, mScaleFactor);

        if (bmp != null) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // set background to transparent
            canvas1.drawColor(Color.WHITE); // set background to transparent
            canvas1.drawBitmap(bmp, 0, 0, null);
            holder.unlockCanvasAndPost(canvas);
        }
        canvas1.restore();
        invalidate();
    }


    private class PanListener extends GestureDetector.SimpleOnGestureListener {
        private Rect mContentRect = new Rect(AXIS_X_MIN, AXIS_Y_MIN, 50, 50);

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            float viewportOffsetX = distanceX * mCurrentViewport.width() / mContentRect.width();
            float viewportOffsetY = - distanceY * mCurrentViewport.height() / mContentRect.height();
            // Updates the viewport, refreshes the display.
            setViewportBottomLeft(mCurrentViewport.left + viewportOffsetX, mCurrentViewport.bottom + viewportOffsetY);
            //Log.d("setViewportBottomLeft","setViewportBottomLeft");
            return true;
        }

        private void setViewportBottomLeft(float x, float y) {
            float curWidth = mContentRect.width();
            float curHeight = mContentRect.height();
            x = Math.max(AXIS_X_MIN, Math.min(x, AXIS_X_MAX - curWidth));
            y = Math.max(AXIS_Y_MIN + curHeight, Math.min(y, AXIS_Y_MAX));

            mCurrentViewport.set(x, y - curHeight, x + curWidth, y);
            //Log.d("setViewportBottomLeft","mCurrentViewport = "+mCurrentViewport.toString());
            ViewCompat.postInvalidateOnAnimation(cv);
        }
    }

}
