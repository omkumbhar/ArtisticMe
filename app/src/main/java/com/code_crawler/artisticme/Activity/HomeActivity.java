package com.code_crawler.artisticme.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.code_crawler.artisticme.Fragments.AlbumFragment;
import com.code_crawler.artisticme.Fragments.HomeFragment;
import com.code_crawler.artisticme.Methods.CreateDirectory;
import com.code_crawler.artisticme.Methods.PathUtil;
import com.code_crawler.artisticme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class HomeActivity extends AppCompatActivity   {

    FloatingActionButton fab;
    Fragment fragment  ;
    FragmentManager fragmentManager  ;
    FragmentTransaction ft  ;
    final int REQUEST_CODE_CHOOSE = 9999;
    List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fab                         =  findViewById(R.id.fab);
        Intent intent               = getIntent();
        String name                 = intent.getStringExtra("name");
        String email                = intent.getStringExtra("email");
        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadFragment();
            }
        });

    }
    public  FloatingActionButton getFab(){
        return fab;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            try {
                CreateDirectory.moveFiles(mSelected,AlbumFragment.folderName,getApplicationContext());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFragment() {
        fragment                           = new HomeFragment();
        fragmentManager                    = getSupportFragmentManager();
        ft                                 = fragmentManager.beginTransaction();
        ft.replace(R.id.container,fragment,"HomeFrag");
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}
