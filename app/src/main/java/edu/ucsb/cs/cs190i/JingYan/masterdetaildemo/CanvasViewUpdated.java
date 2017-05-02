package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by EYE on 30/04/2017.
 */

public class CanvasViewUpdated extends SurfaceView implements SurfaceHolder.Callback {
    private Canvas canvas;
    private SurfaceHolder holder;
    public Bitmap bmp;


    //for Zoom & pan touch event
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    private float prevRot = 0f;
    private float newRot = 0f;
    float r = 0;


    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid_start = new PointF();
    PointF mid_currrent = new PointF();
    float oldDist = 1f;

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int PAN = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    private static final String TAG = "DebugTag";


    public CanvasViewUpdated(Context context) {
        super(context);
    }

    public CanvasViewUpdated(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);
        setWillNotDraw(false);
    }

    public Matrix getMatrix(){
        return matrix;
    }

    public void setMatrix(Matrix m){
        matrix = m;
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
        invalidate();
        bmp = bitmap;
        canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR); // set background to transparent
        canvas.drawBitmap(bitmap, 0, 0, null);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        PanZoomWithTouch(ev);
        invalidate();//necessary to repaint the canvas
        return true;

    }

    @Override
    public void onDraw(Canvas canvas1) {
        super.onDraw(canvas1);

        Paint paint = new Paint();
        if (bmp != null) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // set background to transparent
            canvas1.drawColor(Color.WHITE); // set background to transparent
            canvas1.drawBitmap(bmp, matrix, paint);
            holder.unlockCanvasAndPost(canvas);
        }

    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    void PanZoomWithTouch(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: //when first finger down, get first point
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=single PAN start");
                mode = PAN;
                break;

            case MotionEvent.ACTION_POINTER_DOWN://when 2nd finger down, get second point
                Log.i("lastEvent", "2 fingers start ");
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid_start, event); //then get the mid point as centre for zoom
                    mode = ZOOM;
                }
                prevRot = rotation(event);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:       //when both fingers are released, do nothing
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:     //when fingers are dragged, transform matrix for panning
                if (mode == PAN) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x,
                            event.getY() - start.y);
                } else if (mode == ZOOM) { //if pinch_zoom, calculate distance ratio for zoom
                    float newDist = spacing(event);

                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid_start.x, mid_start.y);

                        //as well as translateion
                        midPoint(mid_currrent, event);
                        matrix.postTranslate(mid_currrent.x - mid_start.x,
                                mid_currrent.y - mid_start.y);
                        //as well as rotation
                        newRot = rotation(event);
                        r = newRot - prevRot;
                        matrix.postRotate(r, mid_currrent.x,mid_currrent.y);
                        Log.i("postRotate", "postRotate = " + r);
                    }
                }
                break;
        }
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}