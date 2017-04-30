package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EYE on 27/04/2017.
 */

public class ComicFragment extends SavableFragment {
    private int number;
    private static final int MAX_NUMBER = 1827;
    private static final String URL_START = "https://xkcd.com/";
    private Bitmap bmp;
    private static final String TextExtra = "SavedBitmap";
    private CanvasView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comic, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = (CanvasView) getActivity().findViewById(R.id.canvas);

        final EditText editNumber = (EditText) getActivity().findViewById(R.id.number);

        // restore state
        if(number!=0){
            editNumber.setText(number+"");
            setImageFromXKCD(number);
        }

        Button comicButton = (Button) getActivity().findViewById(R.id.comic_button);
        comicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Button Clicked.", Toast.LENGTH_LONG).show();

                if (!Objects.equals(editNumber.getText().toString(), "")) {
                    number = Integer.parseInt(editNumber.getText().toString());

                    // catch exception number input
                    if (number > MAX_NUMBER) {
                        Toast.makeText(getActivity(), getString(R.string.Comic_Exception), Toast.LENGTH_LONG).show();
                        Random rand = new Random();
                        number = rand.nextInt(MAX_NUMBER+1);
                    }

                    ((TextView) getActivity().findViewById(R.id.test_text)).setText("comic " + number);

                    // retrieve online image in a new thread and set bitmap to imageView
                    setImageFromXKCD(number);
                }

                // hide keyboard to get more space
                hideSoftKeyboard(getActivity());
            }
        });
    }


    void setImageFromXKCD(final int num){

        // retrieve online resource in a new thread
        new AsyncTask<Integer, Void, Bitmap>() {
            private final String ImageUrlRegex = "(https://imgs\\.xkcd\\.com/comics/.*)";
            private final Pattern ImageUrlPattern = Pattern.compile(ImageUrlRegex);
            private String IMAGE_URL = URL_START + num;

            @Override
            protected Bitmap doInBackground(Integer... params) {

                try {
                    URL pageUrl = new URL(IMAGE_URL);
                    URLConnection connection = pageUrl.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder pageHtmlBuilder = new StringBuilder();
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        pageHtmlBuilder.append(inputLine);
                        pageHtmlBuilder.append('\n');
                    }
                    reader.close();
                    Matcher matcher = ImageUrlPattern.matcher(pageHtmlBuilder.toString());
                    if (matcher.find()) {
                        URL imageUrl = new URL(matcher.group(1));
                        URLConnection imageConnection = imageUrl.openConnection();
                        return BitmapFactory.decodeStream(imageConnection.getInputStream());
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    bmp = bitmap;
                }
            }
        }.execute();
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putInt(TextExtra,number);
    }

    @Override
    public void restoreState(Bundle bundle) {
        if(bundle!=null){
            number = bundle.getInt(TextExtra);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
