package com.code_crawler.artisticme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.code_crawler.artisticme.Fragments.HomeFragment;
import com.code_crawler.artisticme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class HomeActivity extends AppCompatActivity   {
    String folderName;
    FloatingActionButton fab;
    Fragment fragment  ;
    FragmentManager fragmentManager  ;
    FragmentTransaction ft  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        fab                         =  findViewById(R.id.fab);

        Intent intent       = getIntent();
        String name         = intent.getStringExtra("name");
        String email        = intent.getStringExtra("email");

        //Toast.makeText(this, name +" has this email "+ email, Toast.LENGTH_SHORT).show();






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addFolderAlert();
                    }
                });





            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadFragment();
            }
        });



    }

    private void loadFragment() {
        fragment                           = new HomeFragment();
        fragmentManager             = getSupportFragmentManager();
        ft                      = fragmentManager.beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
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
                        if( !folderName.equals("")) {
                            createDirectory();
                            loadFragment();

                        }
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
    private void createDirectory() {
        String DirectoryPath = Environment.getExternalStorageDirectory()+"/Artwork/"+folderName;
        File dir = new File(DirectoryPath);
        if( !dir.exists())
            dir.mkdir();
    }


}
