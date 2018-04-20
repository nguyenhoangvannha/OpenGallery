package com.example.nhvn.opengallery.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nhvn.opengallery.activities.PhotosPagerActivity;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.view.TouchImageView;

import java.io.File;

public class PhotosPagerAdapter extends PagerAdapter {
    Context context;
    Album album;

    public PhotosPagerAdapter(Context context, Album album) {
        this.context = context;
        this.album = album;
    }

    @Override
    public int getCount() {
        return album.getPhotos().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((TouchImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        Glide.with(context).load(new File(album.getPhotos().get(position))).into(imageView);

        TouchImageView touchImageView = new TouchImageView(context);
        //touchImageView.setImageDrawable(imageView.getDrawable());
        touchImageView.setScaleType(TouchImageView.ScaleType.CENTER_INSIDE);

        //Glide.with(context).load(new File(album.getPhotos().get(position))).into(touchImageView);
        touchImageView.setImageURI(Uri.fromFile(new File(album.getPhotos().get(position))));
        ((ViewPager) container).addView(touchImageView, 0);
        touchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PhotosPagerActivity) context).toggle();
            }
        });

//        final ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        Glide.with(context).load(new File(album.getPhotos().get(position))).into(imageView);
//        ((ViewPager) container).addView(imageView, 0);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((PhotosPagerActivity) context).toggle();
//            }
//        });
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(context, "x: " + event.getX() + " y: " + event.getY(), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        return touchImageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((TouchImageView) object);
    }
}
