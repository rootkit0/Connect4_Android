package com.example.connect4;

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

public class ResultsActivity extends AppCompatActivity {

    String email_content;
    String log_content;
    String date_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Get content from intent
        Bundle data = this.getIntent().getExtras();
        String nickname = data.getString("nickname");
        int board_size = data.getInt("board_size");
        Boolean time_status = data.getBoolean("time_status");
        String time = data.getString("time_value");
        String result = data.getString("result");

        EditText dateTime = (EditText)findViewById(R.id.editText);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        this.date_content = df.format(Calendar.getInstance().getTime());
        dateTime.setText(date_content);

        EditText log = (EditText)findViewById(R.id.editText2);
        this.log_content = "Alias: " + nickname + "\n" + "Tamaño tablero: " + board_size;
        if(time_status) {
            this.log_content += " Tiempo total: " + (25 - Integer.parseInt(time)) + "\n";
        }
        this.log_content += "\n" + result + "\n";
        if(time_status) {
            if(Integer.parseInt(time) > 0) {
                this.log_content += "Han sobrado " + time + " segundos!";
            }
            else {
                this.log_content += "Has agotado el tiempo!";
            }
        }
        log.setText(log_content);

        EditText email = (EditText)findViewById(R.id.editText3);
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
}
