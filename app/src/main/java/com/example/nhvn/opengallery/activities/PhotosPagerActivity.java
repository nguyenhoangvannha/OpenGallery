package com.example.nhvn.opengallery.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotosPagerAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.data.provider.ExifHelper;
import com.example.nhvn.opengallery.util.DialogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotosPagerActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    ViewPager viewPager;
    Album album;
    int pos;
    private Menu menu;
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
    private View mContentView;
    PhotosPagerAdapter photoAdapter;
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
    //private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            //mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
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

        setContentView(R.layout.activity_photos_pager);

        mVisible = true;
        //mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.viewPager);


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

    }

    @Override
    protected void onStart() {
        super.onStart();
        album = (Album) getIntent().getSerializableExtra("ALBUM");
        pos = getIntent().getIntExtra("POS", 0);

        File photo = new File(album.getMedias().get(pos));
        getSupportActionBar().setTitle(photo.getName());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.toolbar_color)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Toast.makeText(this, photo.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        viewPager = findViewById(R.id.viewPager);
        photoAdapter = new PhotosPagerAdapter(this, album);
        viewPager.setAdapter(photoAdapter);
        viewPager.setCurrentItem(pos);
        //viewPager.setPageTransformer(true, new ParallaxPageTransformer());


        addEvents();
    }

    private void addEvents(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                File pic = new File(album.getMedias().get(position));
                getSupportActionBar().setTitle(pic.getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photos_pager, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final File file = new File(album.getMedias().get(pos));
        switch (item.getItemId()){
            case R.id.share:
            {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
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
            case R.id.play:
                playItemOnClickListener();
                break;
            case R.id.rename:
                renameItemOnClickListener(file);
                break;
            case R.id.details:
                //ExifHelper.getExifData(this,new File(album.getMedias().get(pos))).toString()
                DialogUtils.showDialog(this, getResources().getString(R.string.details),
                        Html.fromHtml(ExifHelper.getExifData(this,file).toString()),
                        getResources().getString(R.string.ok),null, null , null);
                break;
            case R.id.use:
                Bitmap bitmap = BitmapFactory.decodeFile(file
                        .getAbsolutePath());
                WallpaperManager myWallpaperManager = WallpaperManager
                        .getInstance(getApplicationContext());
                try {
                    myWallpaperManager.setBitmap(bitmap);
                    Toast.makeText(PhotosPagerActivity.this, "setting Wallpaper successfully",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(PhotosPagerActivity.this, "Error setting wallpaper",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void renameItemOnClickListener(final File file) {
        if(file.exists()){
            AlertDialog.Builder builder = new AlertDialog.Builder(PhotosPagerActivity.this);
            builder.setTitle("Rename image");

            View viewInflated = LayoutInflater.from(PhotosPagerActivity.this).inflate(R.layout.text_input_dialog, (ViewGroup) findViewById(R.id.content), false);

            final EditText input = (EditText) viewInflated.findViewById(R.id.input);

            builder.setView(viewInflated);

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String m_Text = input.getText().toString();
                    if(file.getParentFile() != null){
                        String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
                        File to = new File(file.getParentFile(),m_Text+extension);
                        if(file.renameTo(to)){
                            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                            album.getMedias().set(pos, to.getAbsolutePath());
                            photoAdapter.notifyDataSetChanged();
                            photoAdapter = new PhotosPagerAdapter(getApplicationContext(), album);
                            viewPager.setAdapter(photoAdapter);
                            viewPager.setCurrentItem(pos);
                            addEvents();
                            Toast.makeText(PhotosPagerActivity.this, "Rename success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PhotosPagerActivity.this, "Error rename", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(PhotosPagerActivity.this, "Cannot find directory of the image", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }

    private void deleteItemOnClickListener(File file){
        if (file.delete()){
//                                    TODO: Xu ly xoa file voi bo nho ngoai
            album.getMedias().remove(pos);
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            photoAdapter.notifyDataSetChanged();
            photoAdapter = new PhotosPagerAdapter(this, album);
            viewPager.setAdapter(photoAdapter);
            viewPager.setCurrentItem(pos);
            addEvents();
            Toast.makeText(PhotosPagerActivity.this, "Delete sucess", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(PhotosPagerActivity.this, "Delete error", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isSlide = false;
    //Runnable run;
    //TimerTask time;
    //Timer timer = new Timer();
    private void playItemOnClickListener(){
        if(pos >= album.getMedias().size()-1) {
            Toast.makeText(PhotosPagerActivity.this, "End of album", Toast.LENGTH_SHORT).show();
            return;
        }
        final Timer timer = new Timer();
        if(isSlide){
            isSlide = false;
            return;
        }
        menu.findItem(R.id.play).setIcon(R.drawable.ic_pause_circle_outline_white_24dp);
        isSlide = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!isSlide){
                            menu.findItem(R.id.play).setIcon(R.drawable.ic_play_circle_outline_white_24dp);
                            timer.cancel();
                            return;
                        }

                        if (pos >= album.getMedias().size()) {
                            menu.findItem(R.id.play).setIcon(R.drawable.ic_play_circle_outline_white_24dp);
                            timer.cancel();
                            pos--;
                            // Showing a toast for just testing purpose
                            Toast.makeText(getApplicationContext(), "Play done",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            viewPager.setCurrentItem(pos++);
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    public void toggle() {
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
        //mControlsView.setVisibility(View.GONE);
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
