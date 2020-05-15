package com.code_crawler.artisticme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.code_crawler.artisticme.R;

import java.io.File;
import java.util.ArrayList;

public class PagerViewAdapter extends PagerAdapter {
    Context context;
    ArrayList<File> imagePaths;
    LayoutInflater layoutInflater;

    public PagerViewAdapter(Context context, ArrayList<File> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return imagePaths.size()  ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater ) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.pager_image_view,container,false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.pagerImageView);

        Glide.with(context)
                .load(imagePaths.get(position))
                .centerCrop()
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(imageView);

        container.addView(imageView);

        return itemView;

    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
