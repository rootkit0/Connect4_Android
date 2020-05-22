package com.example.connect4.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.connect4.R;
import com.example.connect4.activities.DBHelper;
import com.example.connect4.activities.ImageAdapter;
import com.example.connect4.activities.ResultsActivity;
import com.example.connect4.logic.Game;
import com.example.connect4.logic.Move;
import com.example.connect4.logic.Position;
import com.example.connect4.logic.Status;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private int counter = 25;
    private String nickname;
    private int num_columns;
    private Boolean timer_status;
    private int column_width;
    private Game game_instance;
    private ImageView turn;
    private TextView timer;
    private CountDownTimer count_timer;
    private GridView board;
    private ImageAdapter boardAdapter;
    private String start_drop;
    private String final_drop;
    private DateFormat df;
    private String result;
    //Listener
    OnChangeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedPreferences();
    }

    private void getSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.nickname = preferences.getString("nickname", "Jugador 1");
        this.num_columns = Integer.parseInt(preferences.getString("boardSize", "7"));
        this.timer_status = preferences.getBoolean("timer", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        board = getView().findViewById(R.id.gridView);
        df = new SimpleDateFormat("HH:mm:ss");
        this.start_drop = df.format(Calendar.getInstance().getTime());
        this.setColumnWidth(board);
        this.startGame();
        boardAdapter = new ImageAdapter(getActivity(), num_columns, column_width, game_instance);
        board.post(() -> board.setAdapter(boardAdapter));
        this.board.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int column = (position%num_columns);
        if(game_instance.canPlayColumn(column)) {
            Move mv = game_instance.drop(column);
            int played_row = mv.getPosition().getRow();
            int played_column = mv.getPosition().getColumn();
            game_instance.board.cells[played_row][played_column] = 1;
            boardAdapter.notifyDataSetChanged();
            this.final_drop = df.format(Calendar.getInstance().getTime());
            this.listener.onChange(new Position(played_row, played_column), (String) timer.getText(), start_drop, final_drop);
            if(game_instance.checkForFinish()) {
                gameFinished();
            }
            this.playOpponent();
        }
    }

    private void playOpponent() {
        int computer_column = game_instance.playOpponent();
        Move computer_mv = game_instance.drop(computer_column);
        int computer_played_row = computer_mv.getPosition().getRow();
        int computer_played_column = computer_mv.getPosition().getColumn();
        game_instance.board.cells[computer_played_row][computer_played_column] = 2;
        boardAdapter.notifyDataSetChanged();
        this.start_drop = df.format(Calendar.getInstance().getTime());
        if(game_instance.checkForFinish()) {
            gameFinished();
        }
    }

    private void gameFinished() {
        result = "";
        if(timer_status) {
            count_timer.cancel();
        }
        if(game_instance.getStatus() == Status.PLAYER1_WINS) {
            result = "Has ganado!";
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
        else if(game_instance.getStatus() == Status.PLAYER2_WINS) {
            result = "Has perdido!";
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
        else if(game_instance.getStatus() == Status.DRAW) {
            result = "Habeis empatado!";
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
        addToDatabase();
        Intent i = new Intent(getActivity(), ResultsActivity.class);
        i.putExtra(String.valueOf(R.string.intent_game_timevalue), timer.getText());
        i.putExtra(String.valueOf(R.string.intent_game_result), result);
        startActivity(i);
        getActivity().finish();
    }

    private void setColumnWidth(GridView board) {
        board.setNumColumns(num_columns);
        if(num_columns == 5) {
            column_width = 210;
            board.setColumnWidth(210);
        }
        else if(num_columns == 6) {
            column_width = 175;
            board.setColumnWidth(175);
        }
        else {
            column_width = 150;
            board.setColumnWidth(150);
        }
    }

    private void startGame() {
        game_instance = new Game(num_columns, num_columns, 4);
        turn = getView().findViewById(R.id.imageView3);
        turn.setImageResource(R.drawable.turn_red);
        this.startTimer();
    }

    private void startTimer() {
        timer = getView().findViewById(R.id.textView8);
        count_timer = null;
        if(timer_status) {
            timer.setTextColor(Color.RED);
            count_timer = new CountDownTimer(26000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(counter));
                    counter--;
                }
                @Override
                public void onFinish() {
                    game_instance.setTimeFinished();
                    result = "Has agotado el tiempo!";
                    addToDatabase();
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), ResultsActivity.class);
                    i.putExtra(String.valueOf(R.string.intent_game_timevalue), timer.getText());
                    i.putExtra(String.valueOf(R.string.intent_game_result), result);
                    startActivity(i);
                    getActivity().finish();
                }
            }.start();
        }
        else {
            timer.setText(String.valueOf(counter));
            timer.setTextColor(Color.BLUE);
        }
    }

    private void addToDatabase() {
        DBHelper logDbHelper = new DBHelper(getActivity(), "Matches", null, 3);
        SQLiteDatabase db = logDbHelper.getWritableDatabase();
        if(db != null) {
            ContentValues entry = new ContentValues();
            entry.put("nickname", this.nickname);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
            entry.put("date", df.format(Calendar.getInstance().getTime()));
            entry.put("board_size", this.num_columns);
            entry.put("timer_status", this.timer_status);
            if(this.timer_status) {
                entry.put("timer", (String) timer.getText());
            }
            entry.put("result", this.result);
            try {
                db.insert("Matches", null, entry);
                Toast.makeText(getActivity(), "Partida guardada a la BD correctamente", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e) {
                Toast.makeText(getActivity(), "Error al guardar la partida en la BD", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnChangeListener {
        void onChange(Position pos, String timer_value, String start, String end);
    }

    public void setChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnChangeListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
    }

    public void onRestoreInstance(Bundle savedInstanceState) {
        this.game_instance = (Game) savedInstanceState.getSerializable("game");
        this.boardAdapter = new ImageAdapter(getActivity(), num_columns, column_width, game_instance);
    }

    public Serializable getGameState() {
        return this.game_instance;
    }
}
