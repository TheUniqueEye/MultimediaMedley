package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

/**
 * Created by EYE on 29/04/2017.
 */

public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
    private Canvas imgCanvas;

    private SurfaceHolder holder;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);

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
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(bitmap, 0, 0, null);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }

        }
        return true;
    }


}
