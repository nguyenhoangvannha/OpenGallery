package com.example.nhvn.opengallery.data.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static HashMap<String, Album> mAlbums = new HashMap<String, Album>();
    private static ArrayList<Album> albums = new ArrayList<>();
    public static ArrayList<Album> getAlbums(Context context) {
        mAlbums.clear();
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
                data = cur.getString(dataColumn);
                //name = cur.getString(nameColumn);
                //date = cur.getString(dateColumn);
//                des = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
//                title = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.TITLE));
                if(mAlbums.containsKey(bucket)){
                    mAlbums.get(bucket).getPhotos().add(data);
                } else{
                    ArrayList<String> photos = new ArrayList<>();
                    File file = new File(data);
                    photos.add(data);
                    Album album = new Album(bucket, file.getParent(), photos);
                    mAlbums.put(bucket, album);
                }
            } while (cur.moveToNext());
            Log.i("ALBUMS", albums.toString());
        }
        Iterator iterator = mAlbums.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Album> entry = (Map.Entry) iterator.next();
            albums.add(entry.getValue());
        }
        return albums;
    }
    public static Bitmap getThumbnail(ContentResolver cr, String path) throws Exception {
        Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.MediaColumns._ID }, MediaStore.MediaColumns.DATA + "=?", new String[] {path}, null);
        if (ca != null && ca.moveToFirst()) {
            int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
            ca.close();
            return MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null );
        }
        ca.close();
        return null;

    }

    //https://stackoverflow.com/questions/21392972/how-to-load-large-images-in-android-and-avoiding-the-out-of-memory-error
    public static Bitmap decodeSampledBitmapFromResource(String resId, int reqWidth, int reqHeight) {

// First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);

// Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

// Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeFile(resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 3;
            final int halfWidth = width / 3;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
