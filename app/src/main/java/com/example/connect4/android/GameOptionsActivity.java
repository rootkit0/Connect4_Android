package com.example.connect4.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.R;

public class GameOptionsActivity extends AppCompatActivity {

    private EditText nickname;
    private RadioGroup sizes_radioGroup;
    private Switch timer_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoptions);

        //Get nickname, board size and timer status from layout
        nickname = (EditText)findViewById(R.id.editText);
        sizes_radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        timer_status = (Switch)findViewById(R.id.switch1);

        Button btn_startGame = (Button)findViewById(R.id.button4);
        btn_startGame.setOnClickListener(new startGame(nickname, sizes_radioGroup, timer_status));
    }

    private class startGame implements View.OnClickListener {

        private EditText nickname;
        private RadioGroup sizes_radioGroup;
        private Switch timer_status;

        //Constructor
        public startGame(EditText nickname, RadioGroup sizes_radioGroup, Switch timer_status) {
            this.nickname = nickname;
            this.sizes_radioGroup = sizes_radioGroup;
            this.timer_status = timer_status;
        }

        @Override
        public void onClick(View v) {
            if(this.nickname.getText().toString().equals("")) {
                Toast.makeText(GameOptionsActivity.this, R.string.nickname_error, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(GameOptionsActivity.this, GameActivity.class);
                //Get nickname and add it to intent
                String nickname = this.nickname.getText().toString();
                i.putExtra(String.valueOf(R.string.intent_options_nickname), nickname);
                //Get timer status and add it to intent
                Boolean timer_status = this.timer_status.isChecked();
                i.putExtra(String.valueOf(R.string.intent_options_timer), timer_status);
                //Parse selected radio button content and add it to intent
                switch(sizes_radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        i.putExtra(String.valueOf(R.string.intent_options_size), 7);
                        break;
                    case R.id.radioButton2:
                        i.putExtra(String.valueOf(R.string.intent_options_size), 6);
                        break;
                    case R.id.radioButton3:
                        i.putExtra(String.valueOf(R.string.intent_options_size), 5);
                        break;
                }
                startActivity(i);
            }
        }
    }
}
