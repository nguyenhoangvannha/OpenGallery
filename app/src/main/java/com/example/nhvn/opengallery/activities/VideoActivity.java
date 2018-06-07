package com.example.nhvn.opengallery.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.data.provider.ExifHelper;
import com.example.nhvn.opengallery.util.DialogUtils;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends AppCompatActivity {
    Album videos;
    int pos;
    private int position = 0;
    private MediaController mediaController;
    //private View mContentView;
    private VideoView mContentView;
    private boolean mVisible;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video_view, menu);
        return super.onCreateOptionsMenu(menu);
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

            Toast.makeText(this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
            finish();
        } else{
            Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
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



    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_videos);

        mVisible = true;
        mContentView = (VideoView)findViewById(R.id.videoView);
        videos = (Album) getIntent().getSerializableExtra("VIDEOS");
        pos = getIntent().getIntExtra("POS", 0);
        File video = new File(videos.getMedias().get(pos));

        getSupportActionBar().setTitle(video.getName());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.toolbar_color)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("CurrentPosition", mContentView.getCurrentPosition());
        mContentView.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Lấy lại ví trí video đã chơi.
        position = savedInstanceState.getInt("CurrentPosition");
        mContentView.seekTo(position);
        mContentView.start();
    }


    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
