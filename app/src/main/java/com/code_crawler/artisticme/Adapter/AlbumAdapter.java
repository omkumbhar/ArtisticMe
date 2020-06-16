package com.code_crawler.artisticme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.code_crawler.artisticme.R;

import java.io.File;
import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<File> imagePaths;
    private ItemClickListener mClickListener;

    public AlbumAdapter(Context context, ArrayList<File> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public AlbumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load(imagePaths.get(position))
                .centerCrop()
                .placeholder(R.mipmap.ic_app_icon_round /*R.drawable.ic_image_black_24dp*/)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.albumImage);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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


    //To retain changes made in view in that view only
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public String getItem(int id) {
        return imagePaths.get(id).toString();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void  onItemLongClick(View view, int position);
    }
}
