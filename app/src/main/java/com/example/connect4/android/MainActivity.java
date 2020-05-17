package com.example.connect4.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.connect4.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_startGame = (Button)findViewById(R.id.button);
        btn_startGame.setOnClickListener(new startGame());

        Button btn_gameRules = (Button)findViewById(R.id.button1);
        btn_gameRules.setOnClickListener(new gameRules());

        Button btn_exitGame = (Button)findViewById(R.id.button2);
        btn_exitGame.setOnClickListener(new exitGame());
    }

    private class startGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, GameOptionsActivity.class);
            startActivity(i);
        }
    }

    private class gameRules implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, HelpActivity.class);
            startActivityForResult(i, 1);
        }
    }

    private class exitGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            System.exit(0);
        }
    }
}
