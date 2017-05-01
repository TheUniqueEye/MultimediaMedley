package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by EYE on 30/04/2017.
 */

public class AnimationFragment extends SavableFragment {
    private ImageView backgroundImage;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private float x = 100, y = 200;
    private int radius = 50;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        backgroundImage = new ImageView(getActivity());
        //backgroundImage.setBackgroundColor(Color.GRAY);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radius, paint);

        backgroundImage.setImageBitmap(bitmap);
        return backgroundImage;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(Bundle bundle) {

    }
}
