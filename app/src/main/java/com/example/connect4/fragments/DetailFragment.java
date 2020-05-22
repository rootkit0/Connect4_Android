package com.example.connect4.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.connect4.R;
import com.example.connect4.activities.DBHelper;

public class DetailFragment extends Fragment {
    private TextView details;
    private String nickname;
    private int board_size;
    private boolean timer_status;
    private String date;
    private int timer;
    private String result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedPreferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.nickname = preferences.getString("nickname", "Jugador 1");
        this.board_size = Integer.parseInt(preferences.getString("boardSize", "7"));
        this.timer_status = preferences.getBoolean("timer", false);
    }

    public void getDetails(long id) {
        this.details = getView().findViewById(R.id.queryDetails);
        DBHelper dbHelper = new DBHelper(getActivity(), "Matches", null, 3);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        setCursor(String.valueOf(id), db);
    }

    public void setCursor(String id, SQLiteDatabase db) {
        String[] dbColumns = new String[]{"_id", "nickname", "date", "board_size", "timer_status", "timer", "result"};
        Cursor cursor = db.query("Matches", dbColumns, "_id=?", new String[]{id}, null, null, null);
        //Find the item moving the cursor
        if(cursor.moveToFirst()) {
            do {
                this.nickname = cursor.getString(1);
                this.date = cursor.getString(2);
                this.board_size = cursor.getInt(3);
                if(cursor.getInt(4) == 1) {
                    this.timer_status = true;
                }
                else {
                    this.timer_status = false;
                }
                this.timer = cursor.getInt(5);
                this.result = cursor.getString(6);
            } while(cursor.moveToNext());
            this.setDetails();
        }
        cursor.close();
        db.close();
    }

    private void setDetails() {
        //When the cursor has found the positon print the details
        String detailsText = "Alias: " + this.nickname + "\n" + "Fecha: " + this.date + "\n" + "Tamano tablero: " + this.board_size + "\n";
        if(this.timer_status) {
            detailsText += "Temporizador activado" + "\n" + "Tiempo restante: " + this.timer + "\n";
        }
        else {
            detailsText += "Temporizador desactivado" + "\n";
        }
        detailsText += "Resultado: " + this.result;
        this.details.setText(detailsText);
    }
}
