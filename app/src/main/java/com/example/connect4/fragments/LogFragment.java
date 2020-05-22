package com.example.connect4.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.connect4.R;
import com.example.connect4.logic.Position;

public class LogFragment extends Fragment {
    private String nickname;
    private int board_size;
    private boolean timer_status;
    private TextView logs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedPreferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.log_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.logs = getView().findViewById(R.id.logsContent);
        this.initLogs();
    }

    private void getSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.nickname = preferences.getString("nickname", "Jugador 1");
        this.board_size = Integer.parseInt(preferences.getString("boardSize", "7"));
        this.timer_status = preferences.getBoolean("timer", false);
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
