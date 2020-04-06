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
        int board_size = data.getInt("board_size");
        Boolean timer = data.getBoolean("timer_status");

        GridView board7 = (GridView) findViewById(R.id.gridView);
        board7.setAdapter(new ImageAdapter(this));
    }
}
