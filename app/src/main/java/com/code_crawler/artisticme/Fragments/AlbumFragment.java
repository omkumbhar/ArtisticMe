package com.code_crawler.artisticme.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.code_crawler.artisticme.Activity.HomeActivity;
import com.code_crawler.artisticme.Adapter.AlbumAdapter;
import com.code_crawler.artisticme.Methods.DeleteFiles;
import com.code_crawler.artisticme.Methods.Deletion;
import com.code_crawler.artisticme.Methods.LoadFiles;
import com.code_crawler.artisticme.Methods.PermissionsRequest;
import com.code_crawler.artisticme.Activity.PhotoViewActivity;
import com.code_crawler.artisticme.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;


public class AlbumFragment extends Fragment implements AlbumAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView imageRecycler;
    public static String folderName;
    private AlbumAdapter albumAdapter;
    private final int REQUEST_CODE_CHOOSE = 9999;
    private Parcelable recyclerViewState;
    boolean isImagePickCalled = false;
    boolean isMulSelectionOn = false;
    private View appBar;
    private TextView selectionCountTextView;
    private int selectedItems = 0;

    private ArrayList<File> imagePaths ;
    ArrayList<File> selectedImages;

    private OnBackPressedCallback callback;

    private  ArrayList<View> selectedViews;

    TextView textView;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @omkar omk2ar sssss  2.
     * @param param1 Parametr 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Show menu items on app bar
        setHasOptionsMenu(true);

        assert getArguments() != null;
        folderName = getArguments().getString("folderName");


        return inflater.inflate(R.layout.fragment_album, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((HomeActivity)getActivity()).getFab().setVisibility(View.VISIBLE);
        super.onViewCreated(view, savedInstanceState);

        //Change app bar text
        changeAppBar();
         /*selectionCountTextView  = HomeFragment.appBar.findViewById(R.id.selectionCount);
        selectionCountTextView .setText(folderName);*/
        //getActivity().invalidateOptionsMenu();

        ((HomeActivity) Objects.requireNonNull(getActivity())).getFab()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filePicker();
                    }
                });

        imageRecycler = view.findViewById(R.id.imageRecycler);

        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageRecycler.setLayoutManager(new GridLayoutManager(getContext(),4));
                if(PermissionsRequest.isPermGranted(getContext())) {
                    imagePaths = new ArrayList<>();
                    imagePaths = LoadFiles.loadImages(folderName);
                    loadImagesInView(Objects.requireNonNull( imagePaths   ));
                }
                else
                    PermissionsRequest.requestPermission(getActivity());
            }
        });






        // Handle back button pressed
        callback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                deselectAllItems(selectedViews);
            }
        };
        //Adding back button pressed to listener
        getActivity().getOnBackPressedDispatcher().addCallback(this, callback );







    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), ""+isImagePickCalled, Toast.LENGTH_SHORT).show();
        if(isImagePickCalled){
            isImagePickCalled = false;
            recyclerViewState = null;

        }else {

            recyclerViewState = Objects.requireNonNull(imageRecycler.getLayoutManager()).onSaveInstanceState();
        }

    }

    private void filePicker() {
        isImagePickCalled = true;
        //Toast.makeText(getActivity(), "imgpicker", Toast.LENGTH_SHORT).show();
        Matisse.from(getActivity())
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(10)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE );
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "fragment", Toast.LENGTH_SHORT).show();




        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == -1) {
            mSelected = Matisse.obtainResult(data);
            try {
                CreateDirectory.moveFiles(mSelected,AlbumFragment.folderName,getContext());
                Toast.makeText(getActivity(), "fragment", Toast.LENGTH_SHORT).show();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void loadImagesInView(ArrayList<File> imagePaths) {


        if( imagePaths != null  ){
            albumAdapter = new AlbumAdapter(getContext(),imagePaths);
            albumAdapter.setClickListener(this);
            imageRecycler.setAdapter(albumAdapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(recyclerViewState != null  ) {
            imageRecycler.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
        else {
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (PermissionsRequest.isPermGranted(getContext()))
                        loadImagesInView(Objects.requireNonNull(LoadFiles.loadImages(folderName)));
                    else
                        PermissionsRequest.requestPermission(getActivity());
                }
            });
            imageRecycler.scrollToPosition(albumAdapter.getItemCount() - 1);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, int position) {

        if(!isMulSelectionOn){
            showImageFullScreen(position);
        }
        else {
            if( !view.isSelected()  ){
                // Show selected UI to view if multi selected is on and view is not selected
                selectItem(view,position);
            }
            else {
                // Deselcted if view is selected
                deselectItem(view,position);
            }

        }


    }

    private void deselectItem(View view, int position) {
        //view.findViewById(R.id.hoverView).setBackgroundColor(Color.parseColor("#33A7FF"));
        view.findViewById(R.id.hoverView).setAlpha(0f);
        view.setSelected(false);
        selectedImages.remove(imagePaths.get(position));
        selectedViews.remove(view);


        --selectedItems;

        if(selectedItems == 0 )
        {
            //Toast.makeText(getActivity(), ""+selectedItems, Toast.LENGTH_SHORT).show();
            isMulSelectionOn = false;
            getActivity().invalidateOptionsMenu();
            //changeAppBar();
            deselectAllItems(selectedViews);
        }
        else {
            selectionCountTextView.setText( selectedItems+"");
        }



    }

    private void deselectAllItems(ArrayList<View> selectedViews) {

        isMulSelectionOn = false;
        getActivity().invalidateOptionsMenu();
        selectedImages = null;
        selectedItems = 0;
        callback.setEnabled(false);

        for( View view : selectedViews){
            view.setSelected(false);
            //view.findViewById(R.id.hoverView).setBackgroundColor(0x00000000);
            view.findViewById(R.id.hoverView).setAlpha(0f);


        }
        selectedViews = null;

        //ENABLING FAB button
        ((HomeActivity) Objects.requireNonNull(getActivity())).getFab().setClickable(true);
        ((HomeActivity) Objects.requireNonNull(getActivity())).getFab().setAlpha(1f);
    }

    private void selectItem(View view, int position) {
        view.findViewById(R.id.hoverView).setBackgroundColor(Color.parseColor("#33A7FF"));
        view.findViewById(R.id.hoverView).setAlpha(0.6f);
        view.setSelected(true);

        selectionCountTextView.setText( ++selectedItems+"");
        selectedImages.add(imagePaths.get(position));
        selectedViews.add(view);

    }


    @Override
    public void onItemLongClick(View view, int position) {


        if(!isMulSelectionOn){
            //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
            onselection();
            selectItem(view,position);

        }
        else {
            if( !view.isSelected()  ){
                // Show selected UI to view if multi selected is on and view is not selected
                selectItem(view,position);
            }
            else {
                // Deselected if view is selected
                deselectItem(view,position);
            }
        }
    }

    private void onselection() {
        isMulSelectionOn = true;
        getActivity().invalidateOptionsMenu();
        callback.setEnabled(true);
        selectedImages  = new ArrayList<>();
        selectedViews = new ArrayList<>();

        ((HomeActivity) Objects.requireNonNull(getActivity())).getFab().setClickable(false);
        ((HomeActivity) Objects.requireNonNull(getActivity())).getFab().setAlpha(0.5f);


    }
    private void changeAppBar() {
        //Change app bar
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.folder_app_bar);

        // get instance of the app bar
        appBar = ((HomeActivity) getActivity()).getSupportActionBar().getCustomView();
        selectionCountTextView = appBar.findViewById(R.id.selectionCount);

    }


    private void showImageFullScreen(int position) {
        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
        intent.putExtra("folderName",folderName);
        intent.putExtra("Position",position);
        startActivity(intent);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.album_menu,menu);


        //Toast.makeText(getActivity(), " onCreateOptionsMenu  ", Toast.LENGTH_SHORT).show();

        if(!isMulSelectionOn) {
            menu.setGroupVisible(R.id.normal, true);
            selectionCountTextView .setText(folderName);
        }
        else {
            menu.setGroupVisible(R.id.normal, false);
            menu.setGroupVisible(R.id.selection, true);
        }






        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

            case R.id.deleteImg:
               // Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
                deleteShow(selectedImages);
                //Deletion.addFolderAlert(getActivity());
                //Deletion.deleteImages(getActivity(), selectedImages);
                /*isMulSelectionOn = false;
                // To call app bar again
                getActivity().invalidateOptionsMenu();
                imagePaths = LoadFiles.loadImages(folderName);
                loadImagesInView(Objects.requireNonNull( imagePaths ));
                albumAdapter.notifyDataSetChanged();*/

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteShow(ArrayList<File> selectedImages) {


        //Create Alert builder to show progress of deleting images
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Deleting");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setGravity(Gravity.CENTER);
        //layout.setBackgroundColor( Color.parseColor("#62C82A"));

        View view = getActivity().getLayoutInflater().inflate(R.layout.delete_alert, null);

        layout.addView(view);
        builder.setView(layout);

        //AsyncTask task to delete files
        final DeleteFiles deleteFiles = new DeleteFiles(view);
        deleteFiles.execute(selectedImages);


        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                deleteFiles.cancel(true);
            }
        });


        builder.show();






































        /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Deleting");
        View view = getActivity().getLayoutInflater().inflate(R.layout.delete_alert, null);
        builder.setView(view);
        builder.show();
        DeleteFiles deleteFiles = new DeleteFiles(view);
        deleteFiles.execute(selectedImages);*/




    }























}
