package com.example.connect4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int num_columns;
    private int column_width;
    private Game game;
    private ImageView turn;
    private ImageView[] arrayViews;
    // Constructor
    public ImageAdapter(Context c, int num_columns, int column_width, Game game, ImageView turn) {
        this.mContext = c;
        this.num_columns = num_columns;
        this.column_width = column_width;
        this.game = game;
        this.turn = turn;
        this.arrayViews = new ImageView[num_columns * num_columns];
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
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(column_width, column_width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
        imageView.setBackgroundResource(R.drawable.cell);
        imageView.setOnClickListener(new CellClickListener(position));
        arrayViews[position] = imageView;
        return imageView;
    }

    private class CellClickListener implements View.OnClickListener {
        int position;

        CellClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            int column = (position%num_columns);
            if(!game.checkForFinish()) {
                if(game.canPlayColumn(column)) {
                    Move mv = game.drop(column, turn);
                    int played_row = mv.getPosition().getRow();
                    int played_column = mv.getPosition().getColumn();
                    int position = (num_columns * played_row + played_column);
                    arrayViews[position].setImageResource(R.drawable.player1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playOponent();
                }
            }
            else {
                if(game.getStatus() == Status.PLAYER1_WINS) {
                    Toast.makeText(mContext, "Has ganado!", Toast.LENGTH_SHORT).show();
                }
                else if(game.getStatus() == Status.PLAYER2_WINS) {
                    Toast.makeText(mContext, "Has perdido!", Toast.LENGTH_SHORT).show();
                }
                else if(game.getStatus() == Status.DRAW) {
                    Toast.makeText(mContext, "Habeis empatado!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public void playOponent() {
            if(!game.checkForFinish()) {
                int computer_column = game.playOpponent();
                Move computer_mv = game.drop(computer_column, turn);
                int computer_played_row = computer_mv.getPosition().getRow();
                int computer_played_column = computer_mv.getPosition().getColumn();
                int computer_position = (num_columns * computer_played_row + computer_played_column);
                arrayViews[computer_position].setImageResource(R.drawable.player2);
            }
        }
    }
}
