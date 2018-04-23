package com.example.nhvn.opengallery.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spanned;

public class DialogUtils {
    public static void showDialog(Context context, String title, Spanned msg,
                                  String positiveTitle, DialogInterface.OnClickListener setPositiveButton,
                                  String negativeTitle ,DialogInterface.OnClickListener setNegativeButton){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setPositiveButton(positiveTitle, setPositiveButton)
            .setNegativeButton(negativeTitle, setNegativeButton);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
