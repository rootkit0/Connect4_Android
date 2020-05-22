package com.example.connect4.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.R;

public class ResultsActivity extends AppCompatActivity {
    private EditText email;
    private EditText log;
    private EditText dateTime;
    private String email_content;
    private String date_content;
    private String log_content;
    private String nickname;
    private int board_size;
    private boolean timer_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Get time and result from intent
        Bundle data = this.getIntent().getExtras();
        String time = data.getString(String.valueOf(R.string.intent_game_timevalue));
        String result = data.getString(String.valueOf(R.string.intent_game_result));
        //Get shared preferences
        getSharedPreferences();
        //Set the results
        setResults(time, result);

        Button sendEmail = (Button)findViewById(R.id.button4);
        sendEmail.setOnClickListener(new sendEmail());

        Button newMatch = (Button)findViewById(R.id.button5);
        newMatch.setOnClickListener(new newGame());

        Button exit = (Button)findViewById(R.id.button6);
        exit.setOnClickListener(new exitGame());
    }

    private void getSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.nickname = preferences.getString("nickname", "Jugador 1");
        this.board_size = Integer.parseInt(preferences.getString("boardSize", "7"));
        this.timer_status = preferences.getBoolean("timer", false);
    }

    private void setResults(String time, String result) {
        this.dateTime = findViewById(R.id.editText);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        this.date_content = df.format(Calendar.getInstance().getTime());
        this.dateTime.setText(date_content);

        this.log = findViewById(R.id.editText2);
        this.log_content = "Alias: " + nickname + "\n" + "Tamaño tablero: " + board_size;
        if(this.timer_status) {
            this.log_content += " Tiempo total: " + (25 - Integer.parseInt(time)) + "\n";
        }
        this.log_content += "\n" + result + "\n";
        if(this.timer_status) {
            if(Integer.parseInt(time) > 0) {
                this.log_content += "Han sobrado " + time + " segundos!";
            }
        }
        this.log.setText(log_content);

        this.email = findViewById(R.id.editText3);
        this.email_content = email.getText().toString();
    }

    private class sendEmail implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent email_intent = new Intent(Intent.ACTION_SENDTO);
            email_intent.setData(Uri.parse("mailto:" + email_content));
            email_intent.putExtra(Intent.EXTRA_SUBJECT, "Log - " + date_content);
            email_intent.putExtra(Intent.EXTRA_TEXT, log_content);
            startActivity(email_intent);
        }
    }

    private class newGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ResultsActivity.this, GameActivity.class);
            startActivity(i);
        }
    }

    private class exitGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finishAffinity();
            System.exit(0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", this.email.getText().toString());
        outState.putString("log", this.log.getText().toString());
        outState.putString("date", this.dateTime.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.email.setText(savedInstanceState.getString("email"));
        this.log.setText(savedInstanceState.getString("log"));
        this.dateTime.setText(savedInstanceState.getString("date"));
    }
}
