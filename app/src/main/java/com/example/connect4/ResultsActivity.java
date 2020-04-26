package com.example.connect4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Get content from intent
        Bundle data = this.getIntent().getExtras();
        String nickname = data.getString("nickname");
        int board_size = data.getInt("board_size");
        Boolean time_status = data.getBoolean("timer_status");

        EditText dateTime = (EditText)findViewById(R.id.editText);
        final String date_content = Calendar.getInstance().getTime().toString();
        dateTime.setText(date_content);

        EditText log = (EditText)findViewById(R.id.editText2);
        final String log_content = "Alias: " + nickname + ", tamaño tablero: " + board_size;
        log.setText(log_content);

        EditText email = (EditText)findViewById(R.id.editText3);
        final String email_content = email.getText().toString();

        //Buttons
        Button sendEmail = (Button)findViewById(R.id.button4);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_intent = new Intent(Intent.ACTION_SENDTO);
                email_intent.setData(Uri.parse("mailto:" + email_content));
                email_intent.putExtra(Intent.EXTRA_SUBJECT, log_content + " - " + date_content);
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
