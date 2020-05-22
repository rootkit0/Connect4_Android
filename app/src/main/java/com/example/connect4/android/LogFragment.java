package com.example.connect4.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.connect4.R;
import com.example.connect4.logic.Position;

public class LogFragment extends Fragment {
    private Bundle data;
    String nickname;
    int board_size;
    boolean timer_status;
    TextView logs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameActivity gameActivity = (GameActivity) getActivity();
        this.data = gameActivity.getIntent().getExtras();
        this.nickname = data.getString(String.valueOf(R.string.intent_options_nickname));
        this.board_size = data.getInt(String.valueOf(R.string.intent_options_size));
        this.timer_status = data.getBoolean(String.valueOf(R.string.intent_options_timer));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.log_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Init the log content
        this.logs = getView().findViewById(R.id.logsContent);
        this.initLogs();
    }

    public void initLogs() {
        this.logs.append("Alias = " + nickname + "\n");
        this.logs.append("Tamaño tablero = " + board_size + "\n");
        if(timer_status) {
            this.logs.append("Control del tiempo\n");
        }
        else {
            this.logs.append("Sin control del tiempo\n");
        }
    }

    public void addTime(String timer_value, String start, String end) {
        this.logs.append("Tiempo in. tirada = " + start + " " + " Tiempo fin. tirada = " + end + "\n");
        if(timer_status) {
            this.logs.append("Tiempo restante = " + timer_value + " segs\n");
        }
    }

    public void addPosition(Position pos) {
        this.logs.append("Posicion ocupada: (" + pos.getRow() + "," + pos.getColumn() + ")\n");
    }

    public void onRestoreInstance(Bundle savedInstanceState) {
        this.logs.setText(savedInstanceState.getString("logs"));
    }

    public String getLogs() {
        return this.logs.getText().toString();
    }
}
