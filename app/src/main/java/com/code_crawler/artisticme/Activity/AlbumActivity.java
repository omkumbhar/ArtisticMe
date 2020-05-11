package com.code_crawler.artisticme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.code_crawler.artisticme.R;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent       = getIntent();
        String folderName         = intent.getStringExtra("folderName");
        Toast.makeText(this, ""+folderName, Toast.LENGTH_SHORT).show();

    }
}
