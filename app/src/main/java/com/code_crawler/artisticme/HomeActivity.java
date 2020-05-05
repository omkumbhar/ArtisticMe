package com.code_crawler.artisticme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    String homeDirectoryPath ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeDirectoryPath = Environment.getExternalStorageDirectory()+"/Artwork";

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        Toast.makeText(this, name +" has this email "+ email, Toast.LENGTH_SHORT).show();





    }
}
