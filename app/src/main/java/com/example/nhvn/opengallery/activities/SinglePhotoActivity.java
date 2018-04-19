package com.example.nhvn.opengallery.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nhvn.opengallery.R;

import java.io.File;

public class SinglePhotoActivity extends AppCompatActivity {
    String photoPath;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);
        photoPath = getIntent().getStringExtra("PHOTO_PATH");
        File photo = new File(photoPath);
        imageView = findViewById(R.id.photo);
        //imageView.setImageURI(Uri.fromFile(new File(photoPath)));
        setTitle(photo.getName());
        Glide.with(this).load(photo).into(imageView);
    }
}
