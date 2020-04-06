package com.example.connect4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int num_columns;
    private int column_width;
    // Constructor
    public ImageAdapter(Context c, int num_columns, int column_width) {
        mContext = c;
        this.num_columns = num_columns;
        this.column_width = column_width;
    }

    public int getCount() {
        if(num_columns == 5) {
            return board5.length;
        }
        else if(num_columns == 6) {
            return board6.length;
        }
        else {
            return board7.length;
        }
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
            imageView.setLayoutParams(new GridView.LayoutParams(column_width, column_width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
        if(num_columns == 5) {
            imageView.setImageResource(board5[position]);
        }
        else if(num_columns == 6) {
            imageView.setImageResource(board6[position]);
        }
        else {
            imageView.setImageResource(board7[position]);
        }
        return imageView;
    }

    // Keep all Images in array
    public Integer[] board7 = {
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
    };

    public Integer[] board6 = {
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
    };

    public Integer[] board5 = {
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
            R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell, R.drawable.cell,
    };
}
