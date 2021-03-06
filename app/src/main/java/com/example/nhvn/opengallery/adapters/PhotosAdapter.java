package com.example.nhvn.opengallery.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.activities.MainActivity;
import com.example.nhvn.opengallery.data.Album;

import java.io.File;
import java.io.Serializable;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{
    Context context;
    Album album;
    LayoutInflater layoutInflater;

    public PhotosAdapter(Context context, Album album) {
        this.context = context;
        this.album = album;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
//            holder.imgPhoto.setImageURI(Uri.fromFile(new File(album.getPhotos().get(position))));
//            holder.imgPhoto.setImageBitmap(
//                    CPHelper.getThumbnail(context.getContentResolver(), album.getPhotos().get(position)));

            Glide.with(context).load(new File(album.getPhotos().get(position)))
                    .into(holder.imgPhoto);

//            holder.imgPhoto.setImageBitmap(
//                    CPHelper.decodeSampledBitmapFromResource(album.getPhotos().get(position), 115, 115));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)context).onMsgFromFragToMain(album, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return album.getPhotos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }
}
