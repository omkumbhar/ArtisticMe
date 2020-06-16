package com.code_crawler.artisticme.Methods;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.code_crawler.artisticme.R;

import java.io.File;
import java.util.ArrayList;


public class Deletion {

    public  static  void  deleteDir(Context context, ArrayList<String> folderList){
        for ( String folder : folderList){
            Toast.makeText(context, "" +folder, Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean deleteImages(Context context, ArrayList<File> imageList){
       /* for( File file : imageList){
            Toast.makeText(context, ""+ file, Toast.LENGTH_SHORT).show();

        }


        return  imageList.get(0).delete();*/


        /*ProgressDialog progressDialog = new ProgressDialog(context,ProgressDialog.STYLE_HORIZONTAL);*/

        addFolderAlert(context );




        return false;
    }




    private static  void addFolderAlert(Context context) {


        int llPadding = 30;

        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.START);

        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        llParam.gravity = Gravity.CENTER;


        final ProgressBar progressBar = new ProgressBar(context,null,
                android.R.attr.progressBarStyleHorizontal);



        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);




        /*TextView tvText = new TextView(context);
        tvText.setText("Loading ...");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(20);
        tvText.setLayoutParams(llParam);*/

        ll.addView(progressBar);
        //ll.addView(tvText);




        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < 101;i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);
                }


            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Deleting");
        builder.setCancelable(false);
        builder.setView(ll);



        builder.show();
        thread.start();





    }



    public static void addAlert(Context context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Folder Add");
        alertDialog.setMessage("Enter New folder name");

        final EditText input = new EditText(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

            input.setLayoutParams(lp);
        input.setHint("Enter Name");
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_folder_black_24dp);



        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }







}
