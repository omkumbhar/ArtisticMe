package com.code_crawler.artisticme.Methods;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsRequest {

        private static final int REQUEST_WRITE_PERMISSION = 2712;


        public  static  boolean isPermGranted(Context context){
            //Request Permission
            int permForWrite = ContextCompat.checkSelfPermission( context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permForRead = ContextCompat.checkSelfPermission( context, Manifest.permission.READ_EXTERNAL_STORAGE);

            if( permForWrite != PackageManager.PERMISSION_GRANTED && permForRead != PackageManager.PERMISSION_GRANTED )

                return false;

            else

                return  true;
        }


        public static  void requestPermission(Activity context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions(context,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_WRITE_PERMISSION );



        }




}