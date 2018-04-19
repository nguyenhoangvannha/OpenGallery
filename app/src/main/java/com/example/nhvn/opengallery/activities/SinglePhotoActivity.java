package com.example.nhvn.opengallery.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;
import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotoPagerAdapter;
import com.example.nhvn.opengallery.data.Album;

import java.io.File;

public class SinglePhotoActivity extends AppCompatActivity {
    ViewPager viewPager;
    Album album;
    int pos;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pager);
        album = (Album) getIntent().getSerializableExtra("ALBUM");
        pos = getIntent().getIntExtra("POS", 0);

        File photo = new File(album.getPhotos().get(pos));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(photo.getName());
        setSupportActionBar(toolbar);
        Toast.makeText(SinglePhotoActivity.this, photo.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        viewPager = findViewById(R.id.viewPager);
        PhotoPagerAdapter photoAdapter = new PhotoPagerAdapter(this, album);
        viewPager.setAdapter(photoAdapter);
        viewPager.setCurrentItem(pos);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                File pic = new File(album.getPhotos().get(position));
                toolbar.setTitle(pic.getName());
            }

            @Override
            public void onPageSelected(int position) {
                File pic = new File(album.getPhotos().get(position));
                Toast.makeText(SinglePhotoActivity.this, pic.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
