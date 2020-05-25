package com.code_crawler.artisticme.Methods;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.code_crawler.artisticme.Adapter.RecyclerAdapter;
import com.code_crawler.artisticme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Objects;

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

    public static void moveFiles(List<Uri> filesUri, String folderName,Context context) throws URISyntaxException {
        String directoryPath = Environment.getExternalStorageDirectory()+"/Artwork/"+folderName+"/";


        Uri imageUri;
        File target;

        for(Uri uri :  filesUri ){
            File file = new File(Objects.requireNonNull(PathUtil.getPath(context, uri)));
            imageUri= Uri.fromFile(file );
           // Toast.makeText(context, "file = "+imageUri, Toast.LENGTH_SHORT).show();
            target = new File(directoryPath+imageUri.getLastPathSegment());
          //  Toast.makeText(context, "Directory path = "+target, Toast.LENGTH_SHORT).show();
            file.renameTo(target);

            try {
                copyFile(file, target);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



    public static void copyFile(File sourceFile, File destFile) throws IOException {


        try (FileChannel source = new FileInputStream(sourceFile).getChannel(); FileChannel destination = new FileOutputStream(destFile).getChannel()) {
            destination.transferFrom(source, 0, source.size());
        }
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
