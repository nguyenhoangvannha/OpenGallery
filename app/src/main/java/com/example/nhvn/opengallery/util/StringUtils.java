package com.example.nhvn.opengallery.util;

import android.content.Context;
import android.widget.Toast;

public class StringUtils {
    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
