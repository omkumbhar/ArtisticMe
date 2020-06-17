package com.code_crawler.artisticme.Methods;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import com.code_crawler.artisticme.R;

import java.io.File;

import java.util.ArrayList;

public class DeleteFiles extends AsyncTask<ArrayList<File>, Integer,Boolean> {

    @SuppressLint("StaticFieldLeak")
    private View view;
    AlertDialog alert;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;
    private TextView textView;

    public DeleteFiles(View view) {
        this.view = view;
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.progressCounter);
    }

    public void setAlertDialog(AlertDialog alert){
        this.alert = alert;
    }

    @Override
    protected Boolean doInBackground(ArrayList<File>... arrayLists) {

        //Deletion.addFolderAlert(getActivity());
        //Deletion.deleteImages(getActivity(), selectedImages);

        for (int i =0;i<101;i++){



            publishProgress(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if( isCancelled()) break;
        }




        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
        textView.setText(values[0]+"%");

        if(values[0] == 100) alert.cancel();


    }
}
