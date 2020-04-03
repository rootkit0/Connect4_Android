package com.example.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        }
    }

    private class gameRules implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Create intent to run second activity
            Intent i = new Intent(MainActivity.this, AjudaActivity.class);
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
