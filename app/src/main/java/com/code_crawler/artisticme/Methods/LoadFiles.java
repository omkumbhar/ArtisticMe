package com.code_crawler.artisticme.Methods;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class LoadFiles {

    Context context;
    String directoryPath = Environment.getExternalStorageDirectory()+"/";


    public LoadFiles(Context context) {
        this.context = context;
    }

    public ArrayList<File> loadDirectories(String folderName){
        directoryPath+=folderName;
        ArrayList<File> mFiles = new ArrayList<File>();
        File mDirectory;
        String folderPath =   directoryPath;
        mDirectory = new File(folderPath);
        File[] files = mDirectory.listFiles();
        if(files == null) {
            return null;
        }
        for( File file : files ){
            if( file.isDirectory())
                mFiles.add(file);
        }
        return  mFiles ;
    }



}
