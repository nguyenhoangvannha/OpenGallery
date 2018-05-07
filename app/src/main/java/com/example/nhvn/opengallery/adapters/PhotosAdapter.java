package com.example.nhvn.opengallery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.activities.MainActivity;
import com.example.nhvn.opengallery.data.Album;

import java.io.File;

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
        File photo = new File(album.getMedias().get(position));
        final String extension = photo.getPath().substring(photo.getPath().lastIndexOf("."));
        try {
//            holder.imgPhoto.setImageURI(Uri.fromFile(new File(album.getMedias().get(position))));
//            holder.imgPhoto.setImageBitmap(
//                    CPHelper.getThumbnail(context.getContentResolver(), album.getMedias().get(position)));

            Glide.with(context).load(new File(album.getMedias().get(position)))
                        .into(holder.imgPhoto);


//            holder.imgPhoto.setImageBitmap(
//                    CPHelper.decodeSampledBitmapFromResource(album.getMedias().get(position), 115, 115));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(extension.equals("mp4")||extension.equals("mp3")){
                    ((MainActivity)context).onMsgFromFragToMain(album, position, 1);
                //}
                //else{
                //    ((MainActivity)context).onMsgFromFragToMain(album, position, 0);
                //}
            }
        });
    }


    @Override
    public int getItemCount() {
        return album.getMedias().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }
}
