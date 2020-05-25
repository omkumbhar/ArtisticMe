package com.code_crawler.artisticme.Fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.code_crawler.artisticme.Activity.HomeActivity;
import com.code_crawler.artisticme.Adapter.AlbumAdapter;
import com.code_crawler.artisticme.Methods.CreateDirectory;
import com.code_crawler.artisticme.Methods.LoadFiles;
import com.code_crawler.artisticme.Methods.PermissionsRequest;
import com.code_crawler.artisticme.Activity.PhotoViewActivity;
import com.code_crawler.artisticme.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AlbumFragment extends Fragment implements AlbumAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView imageRecycler;
    public static String folderName;
    private AlbumAdapter albumAdapter;
    private final int REQUEST_CODE_CHOOSE = 9999;
    private List<Uri> mSelected;
    private Parcelable recyclerViewState;



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
     *
     * @param param1 Parameter 1.
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


        assert getArguments() != null;
        folderName = getArguments().getString("folderName");


        return inflater.inflate(R.layout.fragment_album, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((HomeActivity)getActivity()).getFab().setVisibility(View.VISIBLE);
        super.onViewCreated(view, savedInstanceState);


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
                if(PermissionsRequest.isPermGranted(getContext()))
                    loadImagesInView(Objects.requireNonNull(   LoadFiles.loadImages(folderName)   ));
                else
                    PermissionsRequest.requestPermission(getActivity());
            }
        });




    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerViewState = Objects.requireNonNull(imageRecycler.getLayoutManager()).onSaveInstanceState();

    }

    private void filePicker() {
        Matisse.from(getActivity())
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(10)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == -1) {
            mSelected = Matisse.obtainResult(data);
            try {
                CreateDirectory.moveFiles(mSelected,AlbumFragment.folderName,getContext());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

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

        if(recyclerViewState != null  )

            imageRecycler.getLayoutManager().onRestoreInstanceState(recyclerViewState);

        else
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(PermissionsRequest.isPermGranted(getContext()))
                        loadImagesInView(Objects.requireNonNull(   LoadFiles.loadImages(folderName)   ));
                    else
                        PermissionsRequest.requestPermission(getActivity());
                }
            });

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

       // Toast.makeText(getContext(), "position in fragment "+ position, Toast.LENGTH_SHORT).show();
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
}
