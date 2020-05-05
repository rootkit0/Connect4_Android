package com.example.connect4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class GridFragment extends Fragment {
    //Variable for the timer
    private int counter = 25;
    //Data of the intent
    private Bundle data;
    private String nickname;
    private int num_columns;
    private Boolean timer_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get data from game activity
        GameActivity gameActivity = (GameActivity) getActivity();
        this.data = gameActivity.getParameters();
        this.nickname = data.getString("nickname");
        this.num_columns = data.getInt("board_size");
        this.timer_status = data.getBoolean("timer_status");
        //Call the fragment layout
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView board = getView().findViewById(R.id.gridView);
        //Set num columns and column width
        int column_width;
        board.setNumColumns(num_columns);
        if(num_columns == 5) {
            column_width = 210;
            board.setColumnWidth(210);
        }
        else if(num_columns == 6) {
            column_width = 175;
            board.setColumnWidth(175);
        }
        else {
            column_width = 150;
            board.setColumnWidth(150);
        }
        //Initialize the game logic instance
        final Game game_instance = new Game(num_columns, num_columns, 4);
        //Turn image
        ImageView turn = getView().findViewById(R.id.imageView3);
        //Display timer
        final TextView timer = getView().findViewById(R.id.textView8);
        CountDownTimer count_timer = null;
        if(timer_status) {
            timer.setTextColor(Color.RED);
            count_timer = new CountDownTimer(26000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(counter));
                    counter--;
                }
                @Override
                public void onFinish() {
                    //Stop the game
                    game_instance.setTimeFinished();
                    Toast.makeText(getActivity(), "Se ha acabado el tiempo!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), ResultsActivity.class);
                    i.putExtra("nickname", nickname);
                    i.putExtra("board_size", num_columns);
                    i.putExtra("time_status", true);
                    i.putExtra("time_value", timer.getText());
                    i.putExtra("result", "Has perdido!");
                    startActivity(i);
                }
            }.start();
        }
        else {
            timer.setText(String.valueOf(counter));
            timer.setTextColor(Color.BLUE);
        }
        //Call the adapter to set the content and actions of the grid view
        ImageAdapter boardAdapter = new ImageAdapter(getActivity(), num_columns, column_width, game_instance, turn, timer, timer_status, count_timer, nickname);
        board.setAdapter(boardAdapter);
    }
}
