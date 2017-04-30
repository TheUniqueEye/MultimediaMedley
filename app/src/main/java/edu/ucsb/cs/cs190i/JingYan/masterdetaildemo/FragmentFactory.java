package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

/**
 * Created by Samuel on 4/20/2017.
 */

public class FragmentFactory {
    public static SavableFragment createFragment(String fragment) {
        switch (fragment) {
            case "Example":
                return new ExampleFragment();
            case "Speech-to-Text":
                return new SpeechToTextFragment();
            case "Text-to-Speech":
                return new TextToSpeechFragment();
            case "Comic":
                return new ComicFragment();
            case "Video":
                return new VideoPlaybackFragment();
            case "Animation":
                return new ExampleFragment();
            default:
                return null;
        }
    }
}
