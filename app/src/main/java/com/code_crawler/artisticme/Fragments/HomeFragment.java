package com.code_crawler.artisticme.Fragments;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.code_crawler.artisticme.Activity.HomeActivity;
import com.code_crawler.artisticme.Adapter.RecyclerAdapter;
import com.code_crawler.artisticme.Methods.Deletion;
import com.code_crawler.artisticme.Methods.LoadFiles;
import com.code_crawler.artisticme.Methods.PermissionsRequest;
import com.code_crawler.artisticme.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerAdapter.ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int REQUEST_WRITE_PERMISSION = 2712;
    private RecyclerView recyView;
    private RecyclerAdapter adapter;
    private LoadFiles loadFiles;
    private String folderName;
    private boolean isMulSelectionOn = false;
    private View appBar;
    private TextView selectionCountTextView;
    ImageButton deleteBtn;
    ArrayList<String> selectedFolders;



    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);























        loadFiles = new LoadFiles(getContext());

        //Giving on click to FAB
        ((HomeActivity) Objects.requireNonNull(getActivity())).getFab()
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFolderAlert();
            }
        });


        recyView = view.findViewById(R.id.recyView);
        recyView.setLayoutManager(new GridLayoutManager(getContext(),3));



        if(PermissionsRequest.isPermGranted(getContext()))
            loadFolders(Objects.requireNonNull(   loadFiles .loadDirectories()   ));
        else
            PermissionsRequest.requestPermission(getActivity());


    }
    private void loadFolders(ArrayList<File> selectFiles) {
        Uri uri;
        ArrayList<String>  folderNames = new ArrayList<>();
        for (File file : selectFiles){
            uri =Uri.fromFile(file);
            folderNames.add( uri.getLastPathSegment() );
        }

        adapter = new RecyclerAdapter(getContext(),folderNames);
        adapter.setClickListener(this);
        recyView.setAdapter(adapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadFolders(Objects.requireNonNull(   loadFiles .loadDirectories()   ));
        }
        else
            Toast.makeText(getContext(), "To work app we need read and write permissions", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(View view, int position) {
      // Toast.makeText(getContext(), ""+ adapter.getItem(position), Toast.LENGTH_SHORT).show();

        if(!isMulSelectionOn)
            openFolder(position);
            // TODO selction on
        else {
            //Toast.makeText(getActivity(), "taped on "+ position, Toast.LENGTH_SHORT).show();
            //view.setVisibility(View.GONE);
            //view.getBackground().setAlpha(128);
           // Toast.makeText(getActivity(), ""+, Toast.LENGTH_SHORT).show();
            if( !(view.getTag()+"").equals("selected")   ){
                view.setTag("selected");
                selectedFolders.add( ((TextView)view.findViewById(R.id.folderName)).getText().toString().trim() );
                view.setBackgroundColor(Color.parseColor("#33A7FF"));
                selectionCountTextView.setText( (Integer.parseInt(selectionCountTextView.getText().toString().trim()) + 1 )+ "" );
            }


        }




    }

    private void openFolder(int position) {
        Bundle args = new Bundle();
        args.putString("folderName",adapter.getItem(position));
        AlbumFragment alFrag = new AlbumFragment();
        alFrag.setArguments(args);
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager().beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.replace(R.id.container, alFrag,"AlbumFrag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemLongClick(View view, int position) {

       // TODO : handle second long presssed after firest long pressed

        if(!isMulSelectionOn  ){
            isMulSelectionOn = true;
            selectedFolders = new ArrayList<>();

            selectedFolders.add( ((TextView)view.findViewById(R.id.folderName)).getText().toString().trim()   );


            view.setTag("selected");
            changeAppBar();
            Toast.makeText(getActivity(), "selected : "+position, Toast.LENGTH_SHORT).show();
            view.setBackgroundColor(Color.parseColor("#33A7FF"));
        }
        else {
            if( !(view.getTag()+"").equals("selected")   ){
                view.setTag("selected");
                selectedFolders.add( ((TextView)view.findViewById(R.id.folderName)).getText().toString().trim()   );
                view.setBackgroundColor(Color.parseColor("#33A7FF"));
                selectionCountTextView.setText( (Integer.parseInt(selectionCountTextView.getText().toString().trim()) + 1 )+ "" );
            }
        }
    }

    private void changeAppBar() {
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.folder_app_bar);
        //getSupportActionBar().setElevation(0);
        appBar = ((HomeActivity) getActivity()).getSupportActionBar().getCustomView();
        initializeViews(appBar);

    }

    private void initializeViews(View appBar) {
        selectionCountTextView = appBar.findViewById(R.id.selectionCount);
        selectionCountTextView.setText(1+"");

        deleteBtn = appBar.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "DeleteButton pressed", Toast.LENGTH_SHORT).show();
                Deletion.deleteDir(getActivity(),selectedFolders);
            }
        });

    }

    public void addFolderAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Folder Add");
        alertDialog.setMessage("Enter New folder name");

        final EditText input = new EditText(getContext());
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
                            Toast.makeText(getContext(), "Please enter valid folder name", Toast.LENGTH_SHORT).show();
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

    private void loadFragment() {
        HomeFragment alFrag = new HomeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.replace(R.id.container, alFrag,"HomeFrag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
