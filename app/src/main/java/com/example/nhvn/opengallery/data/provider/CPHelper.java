package com.example.nhvn.opengallery.data.provider;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.nhvn.opengallery.data.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CPHelper {
    private static HashMap<String, Album> albums = new HashMap<>();
    public static void getAlbums(Context context) {
        albums.clear();
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DESCRIPTION,
                MediaStore.Images.Media.TITLE,
        };

        // content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cur = ((Activity)context).managedQuery(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );

        Log.i("ListingImages", " query count=" + cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String name;
            String data;
            String des;
            String title;
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATE_TAKEN);

            int nameColumn = cur.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);

            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                name = cur.getString(nameColumn);
                data = cur.getString(dataColumn);
                date = cur.getString(dateColumn);
                des = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                title = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.TITLE));
                if(albums.containsKey(bucket)){
                    albums.get(bucket).getPhotos().add(data);
                } else{
                    ArrayList<String> photos = new ArrayList<>();
                    File file = new File(data);
                    photos.add(data);
                    Album album = new Album(bucket, file.getParent(), photos);
                    albums.put(bucket, album);
                }
            } while (cur.moveToNext());
            Log.i("ALBUMS", albums.toString());
        }}
}
