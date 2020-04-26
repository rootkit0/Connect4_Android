package com.example.connect4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        final String nickname = data.getString("nickname");
        final int num_columns = data.getInt("board_size");
        final Boolean timer_status = data.getBoolean("timer_status");

        GridView board = (GridView) findViewById(R.id.gridView);
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
        ImageView turn = (ImageView)findViewById(R.id.imageView3);
        //Display timer
        final TextView timer = findViewById(R.id.textView8);
        CountDownTimer count_timer = null;
        if(timer_status) {
            timer.setTextColor(Color.RED);
            count_timer = new CountDownTimer(25000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(counter));
                    counter--;
                }
                @Override
                public void onFinish() {
                    //Stop the game
                    game_instance.setTimeFinished();
                    Toast.makeText(GameActivity.this, "Se ha acabado el tiempo!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(GameActivity.this, ResultsActivity.class);
                    i.putExtra("nickname", nickname);
                    i.putExtra("board_size", num_columns);
                    i.putExtra("time_status", true);
                    startActivity(i);
                }
            }.start();
        }
        else {
            timer.setText(String.valueOf(counter));
            timer.setTextColor(Color.BLUE);
        }
        //Call the adapter to set the content of the grid view
        final ImageAdapter boardAdapter = new ImageAdapter(this, num_columns, column_width, game_instance, turn, timer_status, count_timer, nickname);
        board.setAdapter(boardAdapter);
    }
}
