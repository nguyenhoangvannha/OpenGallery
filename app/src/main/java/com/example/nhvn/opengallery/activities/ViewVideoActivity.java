package com.example.nhvn.opengallery.activities;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotosPagerAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.data.provider.ExifHelper;
import com.example.nhvn.opengallery.util.DialogUtils;

import java.io.File;
import java.io.IOException;

public class ViewVideoActivity extends AppCompatActivity {
    Album videos;
    private VideoView mContentView;
    int pos;
    private int position = 0;
    private MediaController mediaController;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video_view, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final File file = new File(videos.getMedias().get(pos));
        switch (item.getItemId()){
            case R.id.share:
            {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("video/*");
                Uri uri1 = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                share.putExtra(Intent.EXTRA_STREAM, uri1);
                share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(share, getString(R.string.send_to)));
                break;
            }
            case R.id.delete:
                DialogUtils.showDialog(this, getResources().getString(R.string.delete),
                        getResources().getString(R.string.delete_confirm),
                        getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItemOnClickListener(file);
                            }
                        }, getResources().getString(R.string.no), null);

                break;
            case R.id.details:
                //ExifHelper.getExifData(this,new File(album.getMedias().get(pos))).toString()
                DialogUtils.showDialog(this, getResources().getString(R.string.details),
                        Html.fromHtml(ExifHelper.getExifData(this,file).toString()),
                        getResources().getString(R.string.ok),null, null , null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteItemOnClickListener(File file){
        if (file.delete()){
//                                    TODO: Xu ly xoa file voi bo nho ngoai
            videos.getMedias().remove(pos);
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

            Toast.makeText(ViewVideoActivity.this, "Delete sucess", Toast.LENGTH_SHORT).show();
            finish();
        } else{
            Toast.makeText(ViewVideoActivity.this, "Delete error", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener onClickListenerNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                // ID của file video.
                if(pos >= 0 && pos < videos.getMedias().size()-1)
                {
                    //video_uri++;
                    //int id = getRawResIdByName(video_list[video_uri]);
                    //mContentView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    pos++;
                    File video = new File(videos.getMedias().get(pos));
                    mContentView.setVideoPath(video.getPath());
                    mContentView.start();
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
                    //mContentView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    pos--;
                    File video = new File(videos.getMedias().get(pos));
                    mContentView.setVideoPath(video.getPath());
                    mContentView.start();
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

        mContentView = (VideoView)findViewById(R.id.videoView);
        videos = (Album) getIntent().getSerializableExtra("VIDEOS");
        pos = getIntent().getIntExtra("POS", 0);
        File video = new File(videos.getMedias().get(pos));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        if (mediaController == null) {
            mediaController = new MediaController(this);
            mediaController.setAnchorView(mContentView);
            mContentView.setMediaController(mediaController);
            mediaController.setPrevNextListeners(onClickListenerNext, onClickListenerPrevious);
        }



        try {
            // ID của file video.
            //int id = this.getRawResIdByName(video_list[video_uri]);
            //videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
            mContentView.setVideoPath(video.getPath());

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        mContentView.requestFocus();

        mContentView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                mContentView.seekTo(position);
                if (position == 0) {
                    mContentView.start();
                }

                // Khi màn hình Video thay đổi kích thước
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Neo lại vị trí của bộ điều khiển của nó vào VideoView.
                        mediaController.setAnchorView(mContentView);
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
        savedInstanceState.putInt("CurrentPosition", mContentView.getCurrentPosition());
        mContentView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Lấy lại ví trí video đã chơi.
        position = savedInstanceState.getInt("CurrentPosition");
        mContentView.seekTo(position);
        mContentView.start();
    }
}
