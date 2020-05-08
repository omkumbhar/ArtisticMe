package com.code_crawler.artisticme.Methods;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.code_crawler.artisticme.R;

public class CreateDirectory {
    Context context;
    String folderName;

    public CreateDirectory(Context context) {
        this.context = context;
    }

    public void addFolderAlert() {

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
                        if( !folderName.equals(""))
                            Toast.makeText(context, ""+folderName, Toast.LENGTH_SHORT).show();

                        else
                            Toast.makeText(context, "Please enter valid folder name", Toast.LENGTH_SHORT).show();

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
