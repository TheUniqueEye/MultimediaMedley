package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by EYE on 25/04/2017.
 */

public class SpeechToTextFragment extends SavableFragment {
    static final int SPEECH_TO_TEXT_REQUEST = 1;  // The request code
    static final String PROMPT_TEXT = "This is Samantha. Talk to me.";
    static final String EXCEPTION_TEXT = "Sorry, this function is not supported by your phone.";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.speech_to_text, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        ImageButton speechButton = (ImageButton) getActivity().findViewById(R.id.speech_button);
        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Button Clicked.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); // chooose language
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, PROMPT_TEXT); // prompt text

                try {
                    startActivityForResult(intent,SPEECH_TO_TEXT_REQUEST);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getActivity(),EXCEPTION_TEXT, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(intent!=null) {
            ArrayList<String> list = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = list.get(0);
            if (spokenText != null) {
                TextView speechText = (TextView) getActivity().findViewById(R.id.speech_text);
                speechText.setText(spokenText);
            }
        }
    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(Bundle bundle) {

    }
}
