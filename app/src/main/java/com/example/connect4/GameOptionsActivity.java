package com.example.connect4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoptions);

        EditText nickname = (EditText)findViewById(R.id.editText);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int selected_radioButton = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = (RadioButton)findViewById(selected_radioButton);

        Switch timerStatus = (Switch)findViewById(R.id.switch1);

        Button btn_startGame = (Button)findViewById(R.id.button4);
        btn_startGame.setOnClickListener(new startGame(nickname, selectedRadioButton, timerStatus));
    }

    private class startGame implements View.OnClickListener {

        private EditText nickname;
        private RadioButton selectedRadioButton;
        private Switch timer_status;

        //Constructor
        public startGame(EditText nickname, RadioButton selectedRadioButton, Switch timer_status) {
            this.nickname = nickname;
            this.selectedRadioButton = selectedRadioButton;
            this.timer_status = timer_status;
        }

        @Override
        public void onClick(View v) {
            String nickname = this.nickname.getText().toString();
            String size = this.selectedRadioButton.getText().toString();
            Boolean timer_status = this.timer_status.isChecked();

            TextView test1 = (TextView)findViewById(R.id.textView10);
            test1.setText(nickname);
        }
    }
}
