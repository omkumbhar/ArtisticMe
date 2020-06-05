package com.code_crawler.artisticme.Methods;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class LoadFiles {
    private static String directoryPath = Environment.getExternalStorageDirectory()+"/Artwork";


    public LoadFiles() {}

    public ArrayList<File> loadDirectories(){

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





    public static  ArrayList<File> loadImages(String folderName){
        String albumPath = directoryPath+ "/" +folderName;

        ArrayList<File> images = new ArrayList<File>();
        File mDirectory = new File(albumPath);
        File[] files = mDirectory.listFiles();


        if(files == null) {
            return null;
        }
        for( File file : files ){
            if( !file.isDirectory())
                images .add(file);
        }
        return  images;
    }


}