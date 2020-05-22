package com.example.connect4.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    private String log_content;
    private String date_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Get content from intent
        Bundle data = this.getIntent().getExtras();
        String nickname = data.getString(String.valueOf(R.string.intent_game_nickname));
        int board_size = data.getInt(String.valueOf(R.string.intent_game_size));
        Boolean time_status = data.getBoolean(String.valueOf(R.string.intent_game_timestatus));
        String time = data.getString(String.valueOf(R.string.intent_game_timevalue));
        String result = data.getString(String.valueOf(R.string.intent_game_result));

        dateTime = (EditText)findViewById(R.id.editText);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        this.date_content = df.format(Calendar.getInstance().getTime());
        dateTime.setText(date_content);

        log = (EditText)findViewById(R.id.editText2);
        this.log_content = "Alias: " + nickname + "\n" + "Tamaño tablero: " + board_size;
        if(time_status) {
            this.log_content += " Tiempo total: " + (25 - Integer.parseInt(time)) + "\n";
        }
        this.log_content += "\n" + result + "\n";
        if(time_status) {
            if(Integer.parseInt(time) > 0) {
                this.log_content += "Han sobrado " + time + " segundos!";
            }
        }
        log.setText(log_content);

        email = (EditText)findViewById(R.id.editText3);
        this.email_content = email.getText().toString();

        //Buttons
        Button sendEmail = (Button)findViewById(R.id.button4);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_intent = new Intent(Intent.ACTION_SENDTO);
                email_intent.setData(Uri.parse("mailto:" + email_content));
                email_intent.putExtra(Intent.EXTRA_SUBJECT, "Log - " + date_content);
                email_intent.putExtra(Intent.EXTRA_TEXT, log_content);
                startActivity(email_intent);
            }
        });

        Button newMatch = (Button)findViewById(R.id.button5);
        newMatch.setOnClickListener(new newGame());

        Button exit = (Button)findViewById(R.id.button6);
        exit.setOnClickListener(new exitGame());
    }

    private class newGame implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ResultsActivity.this, GameOptionsActivity.class);
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
