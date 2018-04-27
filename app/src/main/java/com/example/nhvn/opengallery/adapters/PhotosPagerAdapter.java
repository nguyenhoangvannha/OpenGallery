package com.example.nhvn.opengallery.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
        return album.getMedias().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((TouchImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        final TouchImageView touchImageView = new TouchImageView(context);
        //touchImageView.setImageDrawable(imageView.getDrawable());
        touchImageView.setScaleType(TouchImageView.ScaleType.CENTER_INSIDE);

        //Glide.with(context).load(new File(album.getMedias().get(position))).into(touchImageView);
        //touchImageView.setImageURI(Uri.fromFile(new File(album.getMedias().get(position))));
        RequestOptions options = new RequestOptions();
        options.dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(new ColorDrawable(Color.WHITE))
                .override(1184, 1264);
        File image = new File(album.getMedias().get(position));
        if(image.getName().endsWith("gif")){
            SimpleTarget<GifDrawable> target = new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> transition) {
                    resource.start();
                    touchImageView.setImageDrawable(resource);
                    touchImageView.setZoom(1f);
                }
            };
            Glide.with(touchImageView.getContext()).asGif().load(album.getMedias().get(position)).into(target);
        } else {
            Glide.with(context).asBitmap().load(image).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    touchImageView.setImageBitmap(resource);
                }
            });
        }
        ((ViewPager) container).addView(touchImageView, 0);
        touchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PhotosPagerActivity) context).toggle();
            }
        });


//        final ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        Glide.with(context).load(new File(album.getMedias().get(position))).into(imageView);
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
