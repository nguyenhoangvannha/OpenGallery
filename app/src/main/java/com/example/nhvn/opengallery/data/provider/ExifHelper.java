package com.example.nhvn.opengallery.data.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.util.Log;

import com.example.nhvn.opengallery.data.ExifInfo;
import com.example.nhvn.opengallery.util.DataTypeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class ExifHelper {
    public static ExifInfo getExifData(Context context, File image){
        ExifInfo exifInfo = new ExifInfo();
        exifInfo.setTitle(image.getName().substring(0, image.getName().lastIndexOf(".")));
        exifInfo.setPath(image.getAbsolutePath());
        exifInfo.setType(image.getName().substring(exifInfo.getTitle().length() + 1).toUpperCase());
        exifInfo.setSize(DataTypeUtils.getFileSize(image.length()).first);
        exifInfo.setSizeDataType(DataTypeUtils.getFileSize(image.length()).second);
        Uri uri = Uri.fromFile(image);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            ExifInterface exifInterface = new ExifInterface(inputStream);
            int width = exifInterface.getAttributeInt( ExifInterface.TAG_IMAGE_WIDTH, -1 );
            int height = exifInterface.getAttributeInt( ExifInterface.TAG_IMAGE_LENGTH, -1 );
            String date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            int iso = exifInterface.getAttributeInt(ExifInterface.TAG_ISO_SPEED, 0);
            double aperture = exifInterface.getAttributeDouble(exifInterface.TAG_APERTURE_VALUE, 0.0);
            double exposureTime = exifInterface.getAttributeDouble(exifInterface.TAG_EXPOSURE_TIME, 0.0);
            double focalLength = exifInterface.getAttributeDouble(ExifInterface.TAG_FOCAL_LENGTH, 0.0);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifInfo.setWidth(width);
            exifInfo.setHeight(height);
            exifInfo.setDate(date);
            exifInfo.setIso(iso + "");
            exifInfo.setAperture(aperture);
            exifInfo.setExposureTime(exposureTime);
            exifInfo.setFocalDistance(focalLength);
            exifInfo.setOrientation(orientation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        {
//            String[] projection ={
//                    MediaStore.Images.Media.DATE_TAKEN,
//                    MediaStore.Images.Media.SIZE,
//                    MediaStore.Images.Media.ORIENTATION,
//                    MediaStore.Images.Media.WIDTH,
//                    MediaStore.Images.Media.HEIGHT
//            };
//            Uri uri1 = Uri.fromFile(image);
//
//            Cursor cursor = context.getContentResolver().query(uri1, projection, null, null, null);
//            if(cursor.moveToFirst()){
//                int ddd = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
//                Log.i("MediaStoreWIDTH", ddd + "");
//            } else {
//                Log.i("MediaStoreWIDTH",  " sai roi aaa");
//            }
//
//        }
        return exifInfo;
    }
}
