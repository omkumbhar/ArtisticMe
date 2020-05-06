package com.code_crawler.artisticme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.code_crawler.artisticme.Adapter.GridAdapter;
import com.code_crawler.artisticme.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    String homeDirectoryPath ;
    GridView gridView;
    GridAdapter gridAdapter;
    private static final int REQUEST_WRITE_PERMISSION = 2712;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeDirectoryPath = Environment.getExternalStorageDirectory()+"/Artwork";
        gridView = (GridView) findViewById(R.id.gridView);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        Toast.makeText(this, name +" has this email "+ email, Toast.LENGTH_SHORT).show();


        //Request Permission
        int permForWrite = ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permForRead = ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if( permForWrite != PackageManager.PERMISSION_GRANTED && permForRead != PackageManager.PERMISSION_GRANTED ){
            requestPermission();
        }
        else{

            loadFolders(Objects.requireNonNull(SelectFiles()));

        }





























        //String [] foldernmaes = { "Calligraphy","Digital","Painting","Carricature"};

        //ArrayList<File> folderPaths   = SelectFiles();



        //gridAdapter = new GridAdapter(this,foldernmaes);
        //gridView.setAdapter(gridAdapter);













    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_WRITE_PERMISSION );



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadFolders(SelectFiles());
        }
    }

    private void loadFolders(ArrayList<File> selectFiles) {
        Uri uri;
        //String [] foldernmaes = { "Calligraphy","Digital","Painting","Carricature"};
        ArrayList<String>  folderNames = new ArrayList<>();
        for (File file : selectFiles){
            uri =Uri.fromFile(file);
            folderNames.add( uri.getLastPathSegment() );


        }



       gridAdapter = new GridAdapter(this,folderNames);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this, "cliked "+ position, Toast.LENGTH_SHORT).show();
            }
        });




    }

    private ArrayList<File> SelectFiles() {


        ArrayList<File> mFiles = new ArrayList<File>();
        File mDirectory;


        String folderPath =   homeDirectoryPath;
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
