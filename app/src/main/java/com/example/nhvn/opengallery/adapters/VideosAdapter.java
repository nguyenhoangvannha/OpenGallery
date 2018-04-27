package com.example.nhvn.opengallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.data.Album;

import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> videos;
    LayoutInflater layoutInflater;

    public VideosAdapter(Context context, ArrayList<Album> videos) {
        this.context = context;
        this.videos = videos;
        layoutInflater = ((Activity) context).getLayoutInflater();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_video, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgVideo;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
