package com.example.connect4.android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.connect4.R;

public class DetailFragment extends Fragment {

    private TextView details;
    private String nickname;
    private String date;
    private int board_size;
    private boolean timer_status;
    private int timer;
    private String result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
            //When the cursor has found the positon print the details
            if(this.timer_status) {
                this.details.setText(
                        "Alias: " + this.nickname + "\n"
                        + "Fecha: " + this.date + "\n"
                        + "Tamano tablero: " + this.board_size + "\n"
                        + "Temporizador activado" + "\n"
                        + "Tiempo restante: " + this.timer + "\n"
                        + "Resultado: " + this.result
                );
            }
            else {
                this.details.setText(
                        "Alias: " + this.nickname + "\n"
                        + "Fecha: " + this.date + "\n"
                        + "Tamano tablero: " + this.board_size + "\n"
                        + "Temporizador desactivado" + "\n"
                        + "Resultado: " + this.result
                );
            }
        }
        cursor.close();
        db.close();
    }
}
