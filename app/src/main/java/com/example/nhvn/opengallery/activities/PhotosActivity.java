package com.example.nhvn.opengallery.activities;

import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotosAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.fragments.AlbumFragment;
import com.example.nhvn.opengallery.util.MediaSpaceDecoration;

public class PhotosActivity extends AppCompatActivity {

    Album album;
    RecyclerView recyclerView;
    PhotosAdapter photosAdapter;

    public PhotosActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.album = (Album) getIntent().getSerializableExtra("ALBUM");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(album.getName() + "");
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.photos);
        photosAdapter = new PhotosAdapter(this, album);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MediaSpaceDecoration(8));
        recyclerView.setAdapter(photosAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
