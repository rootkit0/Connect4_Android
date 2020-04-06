package com.example.connect4;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get content from intent
        Bundle data = this.getIntent().getExtras();
        String nickname = data.getString("nickname");
        int num_columns = data.getInt("board_size");
        Boolean timer = data.getBoolean("timer_status");

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
        board.setAdapter(new ImageAdapter(this, num_columns, column_width));
    }
}
