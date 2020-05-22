package com.example.connect4.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.R;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button btn_goBack = (Button)findViewById(R.id.button3);
        btn_goBack.setOnClickListener(new goBack());
    }

    private class goBack implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
