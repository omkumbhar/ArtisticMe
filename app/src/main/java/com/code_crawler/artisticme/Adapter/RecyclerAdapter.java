package com.code_crawler.artisticme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code_crawler.artisticme.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<String> folderNames;
    private ItemClickListener mClickListener;

    public RecyclerAdapter(Context context, ArrayList<String> folderNames) {
        this.context = context;
        this.folderNames = folderNames;
    }


    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, final int position) {

        holder.txtView.setText(folderNames.get(position));
    }

    @Override
    public int getItemCount() {
        return folderNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView txtView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView =  itemView.findViewById(R.id.folderName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);
        }

        @Override
            public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mClickListener != null) mClickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }
    }
    public String getItem(int id) {
        return folderNames.get(id);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void  onItemLongClick(View view, int position);
    }
}
