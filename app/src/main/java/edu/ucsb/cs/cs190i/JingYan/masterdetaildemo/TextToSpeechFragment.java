package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by EYE on 25/04/2017.
 */

public class TextToSpeechFragment extends SavableFragment {
    private EditText textField;
    private String typeText;
    private boolean inited = false;
    private TextToSpeech textToSpeech;
    private static final String TextExtra = "SavedTypedText";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_to_speech, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        textField = (EditText) getActivity().findViewById(R.id.text_speech);
        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    inited = true;
                }

            }
        });

        ImageButton textButton = (ImageButton) getActivity().findViewById(R.id.text_button);
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inited){
                    //Toast.makeText(getActivity(), "Button Clicked.", Toast.LENGTH_LONG).show();

                    typeText = textField.getText().toString();
                    textToSpeech.speak(typeText,TextToSpeech.QUEUE_ADD,null);
                }else {
                    Log.e("TTS Exception", "Init Failed!");
                }
            }
        });

        if(typeText!=null){
            textField.setText(typeText);
        }
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putString(TextExtra,typeText);
    }

    @Override
    public void restoreState(Bundle bundle) {
        if(bundle!=null) {
            typeText = bundle.getString(TextExtra);
        }
    }
}
