package com.code_crawler.artisticme.Methods;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.code_crawler.artisticme.Adapter.RecyclerAdapter;
import com.code_crawler.artisticme.R;

import java.io.File;

public class CreateDirectory {
    Context context;
    String folderName;
    RecyclerAdapter adapter;
    boolean result;

    public CreateDirectory(Context context) {
        this.context = context;
    }

    public CreateDirectory(Context context, RecyclerAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public boolean addFolderAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context);
        alertDialog.setTitle("Folder Add");
        alertDialog.setMessage("Enter New folder name");

        final EditText input = new EditText(this.context);
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
                            result = createDirectory();
                        }

                        else {
                            result = false;
                            Toast.makeText(context, "Please enter valid folder name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
        return result;

    }





    private boolean createDirectory() {
        String DirectoryPath = Environment.getExternalStorageDirectory()+"/Artwork/"+folderName;
        File dir = new File(DirectoryPath);
        if( !dir.exists()) {
            dir.mkdir();
            return true;
        }
        return  false;
    }

}
