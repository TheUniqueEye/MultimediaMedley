package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by EYE on 01/05/2017.
 */

public class BallView extends AppCompatImageView {

    float positionX = 100;
    float positionY = 100;
    float lastPositionX;
    float lastPositionY;
    int boundX;
    int boundY;
    int xVelocity = 10;
    int yVelocity = 10;
    int radius = 50;
    Paint paint = new Paint();

    public BallView(Context context) {
        super(context);

    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBallPosition(float x, float y) {
        positionX = x;
        positionY = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boundX = canvas.getWidth();
        boundY = canvas.getHeight();

        // update position
        positionX += xVelocity;
        positionY += yVelocity;

        // at bounds
        if (positionX > boundX - radius) {
            xVelocity = xVelocity * -1;
            positionX = boundX - radius;
        }
        if (positionX < radius) {
            xVelocity = xVelocity * -1;
            positionX = radius;
        }

        if (positionY > boundY - radius) {
            yVelocity = yVelocity * -1;
            positionY = boundY - radius;
        }
        if (positionY < radius) {
            yVelocity = yVelocity * -1;
            positionY = radius;
        }

        // draw ball and background color on canvas
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        canvas.drawColor(Color.LTGRAY);
        canvas.drawCircle(positionX, positionY, radius, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                positionX = ev.getX();
                positionY = ev.getY();
                lastPositionX = positionX;
                lastPositionY = positionX;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                positionX = ev.getX();
                positionY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                xVelocity = (int)(positionX-lastPositionX)/15;
                yVelocity = (int)(positionY-lastPositionY)/20;
                /*
                Random r = new Random();
                Random rr = new Random();
                if(positionY < boundY/2){
                    yVelocity = -rr.nextInt(20);
                }else{
                    yVelocity = rr.nextInt(20);
                }
                if(positionX < boundX/2){
                    xVelocity = -r.nextInt(20);
                }else{
                    xVelocity = r.nextInt(20);
                }
                */
                break;
            }

        }
        return true;
    }

}
