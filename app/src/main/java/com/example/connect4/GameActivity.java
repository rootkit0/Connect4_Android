package com.example.connect4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private String nickname;
    private String board_size;
    private Boolean timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Obtener los intents
        Bundle data = this.getIntent().getExtras();
        String nickname = data.getString("nickname");
        String board_size = data.getString("board_size");
        Boolean timer = data.getBoolean("timer");

        TextView test1 = (TextView)findViewById(R.id.textView7);
        test1.setText(nickname);
        TextView test2 = (TextView)findViewById(R.id.textView11);
        test2.setText(nickname);
        TextView test3 = (TextView)findViewById(R.id.textView12);
        test3.setText(nickname);
    }

}
