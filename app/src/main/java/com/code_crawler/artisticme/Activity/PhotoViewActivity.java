package com.code_crawler.artisticme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.code_crawler.artisticme.Adapter.PagerViewAdapter;
import com.code_crawler.artisticme.Methods.LoadFiles;
import com.code_crawler.artisticme.R;

public class PhotoViewActivity extends AppCompatActivity {
    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;
    String folderName;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        Intent intent               = getIntent();
        viewPager = findViewById(R.id.viewPager);
        folderName =  intent.getStringExtra("folderName");
        position = intent.getIntExtra("Position",0);
        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager()
                ,1, LoadFiles.loadImages(folderName));
        viewPager.setAdapter(pagerViewAdapter);
        viewPager.setCurrentItem(position);


    }
}
