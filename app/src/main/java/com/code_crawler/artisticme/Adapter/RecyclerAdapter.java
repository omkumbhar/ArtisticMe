package com.code_crawler.artisticme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code_crawler.artisticme.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    Context context;
    ArrayList<String> folderNames;
    View view;

    public RecyclerAdapter(Context context, ArrayList<String> folderNames) {
        this.context = context;
        this.folderNames = folderNames;
    }


    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_view,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);



        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, final int position) {

        holder.txtView.setText(folderNames.get(position));



    }

    @Override
    public int getItemCount() {
        return folderNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView txtView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtView = (TextView) itemView.findViewById(R.id.folderName);
        }
    }
}
