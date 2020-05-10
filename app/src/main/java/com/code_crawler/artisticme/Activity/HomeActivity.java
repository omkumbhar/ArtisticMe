package com.code_crawler.artisticme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.code_crawler.artisticme.Adapter.GridAdapter;
import com.code_crawler.artisticme.HomeFragment;
import com.code_crawler.artisticme.Methods.CreateDirectory;
import com.code_crawler.artisticme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    String folderName;
    FloatingActionButton fab;
    CreateDirectory createDirectory;
    private static final int REQUEST_WRITE_PERMISSION = 2712;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        fab                         =  findViewById(R.id.fab);

        Intent intent       = getIntent();
        String name         = intent.getStringExtra("name");
        String email        = intent.getStringExtra("email");

        //Toast.makeText(this, name +" has this email "+ email, Toast.LENGTH_SHORT).show();

        createDirectory = new CreateDirectory(HomeActivity.this);


        loadFragment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if( createDirectory.addFolderAlert())
                   loadFragment();
            }

        });

































    }
    private void loadFragment() {
        Fragment fragment                           = new HomeFragment();
        FragmentManager fragmentManager             = getSupportFragmentManager();
        FragmentTransaction ft                      = fragmentManager.beginTransaction();

        ft.replace(R.id.container,fragment);
        ft.commit();
    }
    public static  void requestPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(context,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_WRITE_PERMISSION );



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    public void addFolderAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Folder Add");
        alertDialog.setMessage("Enter New folder name");

        final EditText input = new EditText(HomeActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint("Enter Name");
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_folder_black_24dp);

        alertDialog.setPositiveButton("CREATE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        folderName = input.getText().toString().trim();
                        if( !folderName.equals(""))
                            Toast.makeText(HomeActivity.this, ""+folderName, Toast.LENGTH_SHORT).show();

                        else
                            Toast.makeText(HomeActivity.this, "Please enter valid folder name", Toast.LENGTH_SHORT).show();

                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }


}
