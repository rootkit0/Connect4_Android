package com.example.connect4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        Button  btn_matches = (Button)findViewById(R.id.button7);
        btn_matches.setOnClickListener(new viewMatches());

        Button btn_exitGame = (Button)findViewById(R.id.button2);
        btn_exitGame.setOnClickListener(new exitGame());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.gameOptions) {
            Intent i = new Intent(this, OptionsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class startGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, GameActivity.class);
            startActivity(i);
        }
    }

    private class gameRules implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(i);
        }
    }

    private class exitGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            System.exit(0);
        }
    }

    private class viewMatches implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, AccessBDActivity.class);
            startActivity(i);
        }
    }
}
