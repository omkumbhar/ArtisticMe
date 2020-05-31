package com.code_crawler.artisticme.Methods;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;


public class Deletion {

    public  static  void  deleteDir(Context context, ArrayList<String> folderList){
        for ( String folder : folderList){
            Toast.makeText(context, "" +folder, Toast.LENGTH_SHORT).show();
        }



    }

}
