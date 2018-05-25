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
import java.net.URLConnection;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{
    Context context;
    Album album;
    LayoutInflater layoutInflater;
    private final String[] videoFileExtension = new String[]{"mp4", "mp3", "mkv", "3gp", "flac", "wav"};

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
        final File photo = new File(album.getMedias().get(position));
        final String extension = photo.getPath().substring(photo.getPath().lastIndexOf("."));
        try {
//            holder.imgPhoto.setImageURI(Uri.fromFile(new File(album.getMedias().get(position))));
//            holder.imgPhoto.setImageBitmap(
//                    CPHelper.getThumbnail(context.getContentResolver(), album.getMedias().get(position)));
            if(isVideoFile(photo)){
                holder.imgPlaybtn.setImageResource(R.drawable.ic_play_button_24dp);
            }

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

                if(isVideoFile(photo)){
                    ((MainActivity)context).onMsgFromFragToMain(album, position, 1);
                }
                else{
                    ((MainActivity)context).onMsgFromFragToMain(album, position, 0);
                }
            }
        });
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public boolean isVideoFile(File file) {
        for (String extension : videoFileExtension)
        {
            if (file.getName().toLowerCase().endsWith(extension))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return album.getMedias().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPhoto;
        public ImageView imgPlaybtn;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            imgPlaybtn = itemView.findViewById(R.id.imgPlayBtn);
        }
    }
}
