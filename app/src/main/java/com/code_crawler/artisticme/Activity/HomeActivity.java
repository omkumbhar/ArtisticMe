package com.code_crawler.artisticme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.GridView;
import android.widget.Toast;

import com.code_crawler.artisticme.Adapter.GridAdapter;
import com.code_crawler.artisticme.R;

public class HomeActivity extends AppCompatActivity {
    String homeDirectoryPath ;
    GridView gridView;
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


        String [] foldernmaes = { "Calligraphy","Digital","Painting","Carricature"};


        GridAdapter gridAdapter = new GridAdapter(this,foldernmaes);

        gridView.setAdapter(gridAdapter);





    }
}
