package com.code_crawler.artisticme.Fragments;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    ActionBar toolbar;
    int selectedItems = 0;

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

        setHasOptionsMenu(true); // for menu items

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



            // Added Arrow button at app bar
        ((HomeActivity) getActivity()). getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  toolbar = ((HomeActivity) getActivity()).getSupportActionBar();

        changeAppBar();
        selectionCountTextView.setText("Home");



        // Handle back button pressed

        /*OnBackPressedCallback callback = new OnBackPressedCallback(isMulSelectionOn) {
            @Override
            public void handleOnBackPressed() {
                // Toast.makeText(getContext(), "Back pressed", Toast.LENGTH_SHORT).show();
                loadFragment();
            }
        };*/


        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(isMulSelectionOn) {
                @Override
                public void handleOnBackPressed() {
                     Toast.makeText(getContext(), "Back pressed", Toast.LENGTH_SHORT).show();
                    loadFragment();
                }
            });
















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

        if(!isMulSelectionOn)
            openFolder(position);
        else {
            if( !(view.getTag()+"").equals("selected") && !(view.getTag()+"").equals("")   ){

                selectedFolders.add( ((TextView)view.findViewById(R.id.folderName)).getText().toString().trim() );
                view.setTag("selected");
                view.setBackgroundColor(Color.parseColor("#33A7FF"));
                try {
                    selectionCountTextView.setText(++selectedItems+"");
                   //selectionCountTextView.setText( (Integer.parseInt(selectionCountTextView.getText().toString().trim()) + 1 )+ "" );
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else if((view.getTag()+"").equals("selected")) {
                    deselectItem(view);
            }
        }
    }

    private void deselectItem(View view) {

        view.setBackgroundColor (Color.parseColor("#E5E5E5"));
        view.setTag("");
        selectedFolders.remove(((TextView)view.findViewById(R.id.folderName)).getText().toString().trim());
       // selectionCountTextView.setText( (Integer.parseInt(selectionCountTextView.getText().toString().trim()) - 1 )+ "" );
        selectionCountTextView.setText((--selectedItems)+"");
        if(selectedItems == 0   ) {
            isMulSelectionOn = false;
            loadFragment();
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


        if(!isMulSelectionOn  ){
            onSelection();
            selectedFolders.add( ((TextView)view.findViewById(R.id.folderName)).getText().toString().trim());
            view.setTag("selected");
            view.setBackgroundColor(Color.parseColor("#33A7FF"));
            selectionCountTextView.setText(++selectedItems+"");

        }
        else {
            if( !(view.getTag()+"").equals("selected")   ){
                view.setTag("selected");
                selectedFolders.add( ((TextView)view.findViewById(R.id.folderName)).getText().toString().trim()   );
                view.setBackgroundColor(Color.parseColor("#33A7FF"));
                //selectionCountTextView.setText( (Integer.parseInt(selectionCountTextView.getText().toString().trim()) + 1 )+ "" );
                selectionCountTextView.setText(++selectedItems+"");
            }
            else {
                deselectItem(view);

            }



        }
    }

    private void onSelection() {
        isMulSelectionOn = true;
        // To call app bar again
        getActivity().invalidateOptionsMenu();
        selectedFolders = new ArrayList<>();
        changeAppBar();
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
        selectionCountTextView.setText(selectedItems+"");

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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.folder_menu,menu);
/*
        if( selectionCountTextView.getText().equals("0")  )
            menu.findItem(R.id.deleteDir).setEnabled(false);
        else
            menu.findItem(R.id.deleteDir).setEnabled(true);*/


        if(!isMulSelectionOn) {
            menu.setGroupVisible(R.id.normal, true);
            selectionCountTextView.setText("Home");
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
                if( isMulSelectionOn  ) {
                    isMulSelectionOn = false;
                    //selectedFolders = null;
                    // To call app bar again
                    //getActivity().invalidateOptionsMenu();
                    loadFragment();
                }
                return true;
            case R.id.deleteDir:
                Deletion.deleteDir(getActivity(),selectedFolders);
                isMulSelectionOn = false;
                // To call app bar again
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.allSelection:
                onSelection();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }




        // TODO : handle on click menu items

        //return super.onOptionsItemSelected(item);




    }
}
