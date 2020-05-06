package com.code_crawler.artisticme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.code_crawler.artisticme.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> folderNames;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, ArrayList<String>  folderNames) {
        this.context = context;
        this.folderNames = folderNames;
    }

    @Override
    public int getCount() {
        return folderNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if( convertView == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.grid_view,null);
            TextView fName = (TextView) view.findViewById(R.id.folderName);

            fName.setText( folderNames.get(position) );
        }





        return  view ;
    }
}
