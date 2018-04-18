package com.example.nhvn.opengallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.data.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.mViewHolder> {
    Context context;
    ArrayList<Album> albums;
    LayoutInflater layoutInflater;

    public AlbumsAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AlbumsAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.item_album, parent, false);
        return new mViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        Album album = albums.get(position);
        Log.i("ALBUM INDEX", position + " " + album.getName());
        if(album.getPhotos().size() > 0){
            try{
                holder.imgCover.setImageURI(Uri.fromFile(new File(album.getPhotos().get(0))));
            } catch (Exception e){

            }
        }
        holder.txtAlbumName.setText(album.getName());
        holder.txtAlbumMediaCount.setText(album.getPhotos().size() + "");
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgCover;
        public TextView txtAlbumName;
        public TextView txtAlbumMediaCount;
        public TextView txtAlbumMediaLabel;

        public mViewHolder(View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgCover);
            txtAlbumName = itemView.findViewById(R.id.album_name);
            txtAlbumMediaCount = itemView.findViewById(R.id.album_media_count);
            txtAlbumMediaLabel = itemView.findViewById(R.id.album_media_label);
        }
    }
}
