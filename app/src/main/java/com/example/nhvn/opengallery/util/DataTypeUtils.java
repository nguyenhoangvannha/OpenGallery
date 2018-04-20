package com.example.nhvn.opengallery.util;

import android.util.Pair;

public class DataTypeUtils {
    public static Pair<Float, String> getFileSize(long size){

        int flag = 0;
        while (size > 1024){
            size /= 1024;
            flag++;
        }
        String ext = "";
        switch (flag){
            case 0:
                ext = "B";
                break;
            case 1:
                ext = "KB";
                break;
            case 2:
                ext = "MB";
                break;
            case 3:
                ext = "GB";
                break;
            case 4:
                ext = "TB";
                break;
            case 5:
                ext = "PB";
                break;
            case 6:
                ext = "EB";
                break;
            case 7:
                ext = "ZB";
                break;
            case 8:
                ext = "YB";
                break;
            default:
                ext = "Too large";
        }
         return new Pair<>((float)size, ext);
    }
}
