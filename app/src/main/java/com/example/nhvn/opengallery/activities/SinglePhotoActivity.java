package com.example.nhvn.opengallery.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotoAdapter;
import com.example.nhvn.opengallery.data.Album;

import java.io.File;

public class SinglePhotoActivity extends AppCompatActivity {
    ViewPager viewPager;
    Album album;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        album = (Album) getIntent().getSerializableExtra("ALBUM");
        pos = getIntent().getIntExtra("POS", 0);
        Log.i("photoactiv", album.getName());
        viewPager = findViewById(R.id.viewPager);
        PhotoAdapter photoAdapter = new PhotoAdapter(this, album);
        viewPager.setAdapter(photoAdapter);
        viewPager.setCurrentItem(pos);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTitle(new File(album.getPhotos().get(position)).getName());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
