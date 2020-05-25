package com.code_crawler.artisticme.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.code_crawler.artisticme.Fragments.PhotoViewFragment;

import java.io.File;
import java.util.ArrayList;

public class PagerViewAdapter extends FragmentStatePagerAdapter {
    Context context;
    ArrayList<File> imagePaths;

    public PagerViewAdapter(@NonNull FragmentManager fm, int behavior,ArrayList<File> imagePaths) {
        super(fm, behavior);
        this.imagePaths = imagePaths;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PhotoViewFragment();
        Bundle args = new Bundle();
        args.putString("ImagePath", imagePaths.get(position).toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }



}
