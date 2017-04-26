package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by EYE on 25/04/2017.
 */

public class TextToSpeechFragment extends SavableFragment {
    private String text;
    private EditText textField;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        textField = new EditText(getActivity());
//        textField.setHint(R.string.TTS_hint);
//        if (text != null) {
//            textField.setText(text);
//        }
//        return textField;
        return inflater.inflate(R.layout.text_to_speech, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        
        textField = (EditText) getActivity().findViewById(R.id.text_speech);


    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(Bundle bundle) {

    }
}
