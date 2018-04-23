package com.example.nhvn.opengallery.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.util.PermissionUtils;
import com.example.nhvn.opengallery.util.StringUtils;

public class SplashScreenActivity extends AppCompatActivity {
    private final  int EXTERNAL_STORAGE_PERMISSIONS_ID = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        start();
    }

    private void start(){
        if(PermissionUtils.isStoragePermissionsGranted(this)){
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivityForResult(intent, 113);
        } else {
            PermissionUtils.requestPermissions(this, EXTERNAL_STORAGE_PERMISSIONS_ID
                    , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case EXTERNAL_STORAGE_PERMISSIONS_ID:
                boolean allPermissionGranted = true;
                for (int result : grantResults){
                    if(result != PackageManager.PERMISSION_GRANTED) allPermissionGranted = false;
                }
                if(!allPermissionGranted){
                    StringUtils.showToast(this, getResources().getString(R.string.request_storage_permission_denied));
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.ic_error_outline_black_24dp).setTitle(R.string.request_storage_permission_title)
                            .setMessage(R.string.request_storage_permission_message).setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton(R.string.try_again, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start();
                        }
                    }).setCancelable(false).show();
                } else{
                    start();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 113:
                finish();
        }
    }
}
