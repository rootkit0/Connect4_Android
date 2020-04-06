package com.example.connect4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(210, 210));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
    };
}
