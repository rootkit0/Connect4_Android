package com.example.connect4.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.connect4.R;
import com.example.connect4.logic.Game;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int num_columns;
    private int column_width;
    private int[][] cells;
    private ImageView imageView;

    public ImageAdapter(Context c, int num_columns, int column_width, Game game_instance) {
        this.mContext = c;
        this.num_columns = num_columns;
        this.column_width = column_width;
        this.cells = game_instance.board.cells;
    }

    public int getCount() {
        return  num_columns * num_columns;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(column_width, column_width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
        int row = position/num_columns;
        int column = position%num_columns;
        if(cells[row][column] == 0) {
            this.imageView.setImageResource(R.drawable.cell);
        }
        else if(cells[row][column] == 1) {
            this.imageView.setImageResource(R.drawable.player1);
        }
        else {
            this.imageView.setImageResource(R.drawable.player2);
        }
        return imageView;
    }
}
