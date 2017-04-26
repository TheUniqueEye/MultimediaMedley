package edu.ucsb.cs.cs190i.JingYan.masterdetaildemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by EYE on 25/04/2017.
 */

public class VideoPlayback extends SavableFragment {
    private VideoView videoView;
    private int videoPosition;
    private static final String TextExtra = "videoBookmark";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ){
        videoView = new VideoView(getActivity());
        Uri uri = Uri.parse("android.resource://"+ getActivity().getPackageName() + "/"+R.raw.bigbuck);
        videoView.setVideoURI(uri);
        videoView.start();
        return videoView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(videoPosition!=0){
            videoView.seekTo(videoPosition);
        }
    }


    @Override
    public void saveState(Bundle bundle) {
        videoPosition = videoView.getCurrentPosition();
        bundle.putInt(TextExtra,videoPosition);
    }

    @Override
    public void restoreState(Bundle bundle) {
        if(bundle!=null){
            videoPosition = bundle.getInt(TextExtra);
        }
    }
}
