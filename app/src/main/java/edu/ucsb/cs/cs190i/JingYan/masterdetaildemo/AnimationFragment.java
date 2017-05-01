package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by EYE on 30/04/2017.
 */

public class AnimationFragment extends SavableFragment {

    private BallView ballView;
    private float ballPositionX=-1, ballPositionY=-1;
    private static final String TextExtraX = "SavedPositionX";
    private static final String TextExtraY = "SavedPositionY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.animation, container, false);
        ballView = new BallView(getContext());

        FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.animation_fragment);
        frameLayout.addView(ballView);

        DrawingThread drawingThread = new DrawingThread(ballView,50);
        drawingThread.start();

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(ballPositionX!=-1 && ballPositionY!=-1){
            ballView.setBallPosition(ballPositionX,ballPositionY);
        }
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putFloat(TextExtraX,ballView.positionX);
        bundle.putFloat(TextExtraX,ballView.positionY);
    }

    @Override
    public void restoreState(Bundle bundle) {
        if(bundle!=null){
            ballPositionX = bundle.getFloat(TextExtraX);
            ballPositionY = bundle.getFloat(TextExtraY);
        }
    }
}
