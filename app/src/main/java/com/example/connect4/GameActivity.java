package com.example.connect4;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    //Variable for the timer
    public int counter = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get content from intent
        Bundle data = this.getIntent().getExtras();
        String nickname = data.getString("nickname");
        int num_columns = data.getInt("board_size");
        Boolean timer_status = data.getBoolean("timer_status");

        GridView board = (GridView) findViewById(R.id.gridView);
        //Set num columns and column width
        int column_width = 0;
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
        //Call the adapter to set the content of the grid view
        board.setAdapter(new ImageAdapter(this, num_columns, column_width));
        //Display timer
        final TextView timer = findViewById(R.id.textView8);
        if(timer_status) {
            timer.setTextColor(Color.RED);
            new CountDownTimer(26000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(counter));
                    counter--;
                }
                @Override
                public void onFinish() {
                    //Stop the game
                }
            }.start();
        }
        else {
            timer.setText("25");
            timer.setTextColor(Color.BLUE);
        }
    }
}
