package com.example.nhvn.opengallery.activities;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.data.Album;

import java.io.File;

public class ViewVideoActivity extends AppCompatActivity {
    Album videos;
    int pos;
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    //int video_uri = 1;
    //private String[] video_list = {"video_1", "video_2", "video_3"};

    private View.OnClickListener onClickListenerNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                // ID của file video.
                if(pos >= 0 && pos < videos.getMedias().size()-1)
                {
                    //video_uri++;
                    //int id = getRawResIdByName(video_list[video_uri]);
                    //videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    pos++;
                    File video = new File(videos.getMedias().get(pos));
                    videoView.setVideoPath(video.getPath());
                    videoView.start();
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
    };
    private View.OnClickListener onClickListenerPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                // ID của file video.
                if(pos > 0 && pos < videos.getMedias().size())
                {
                    //video_uri--;
                    //int id = getRawResIdByName(video_list[video_uri]);
                    //videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    pos--;
                    File video = new File(videos.getMedias().get(pos));
                    videoView.setVideoPath(video.getPath());
                    videoView.start();
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

        videos = (Album) getIntent().getSerializableExtra("VIDEOS");
        pos = getIntent().getIntExtra("POS", 0);
        File video = new File(videos.getMedias().get(pos));

        videoView = (VideoView)findViewById(R.id.videoView);
        if (mediaController == null) {
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            mediaController.setPrevNextListeners(onClickListenerNext, onClickListenerPrevious);
        }



        try {
            // ID của file video.
            //int id = this.getRawResIdByName(video_list[video_uri]);
            //videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
            videoView.setVideoPath(video.getPath());

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // Khi màn hình Video thay đổi kích thước
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Neo lại vị trí của bộ điều khiển của nó vào VideoView.
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
    }

    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Lưu lại vị trí file video đang chơi.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Lấy lại ví trí video đã chơi.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
        videoView.start();
    }
}
