package com.example.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoptions);

        Button btn_startGame = (Button)findViewById(R.id.button4);
        btn_startGame.setOnClickListener(new startGame());
    }

    private class startGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(GameOptionsActivity.this, GameActivity.class);
            startActivity(i);
        }
    }
}
