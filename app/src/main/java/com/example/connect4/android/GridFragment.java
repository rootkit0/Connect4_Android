package com.example.connect4.android;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.connect4.logic.Game;
import com.example.connect4.logic.Move;
import com.example.connect4.logic.Position;
import com.example.connect4.logic.Status;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GridFragment extends Fragment implements AdapterView.OnItemClickListener {
    //Variable for the timer
    private int counter = 25;
    //Data of the intent
    private Bundle data;
    private String nickname;
    private int num_columns;
    private Boolean timer_status;
    //Data for the grid view
    private int column_width;
    private Game game_instance;
    private ImageView turn;
    private TextView timer;
    private CountDownTimer count_timer;
    private ImageAdapter boardAdapter;
    private GridView board;
    private GameActivity gameActivity;
    private String start_drop;
    private String final_drop;
    private DateFormat df;
    private String result;
    //Listener
    OnChangeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get data from game activity
        gameActivity = (GameActivity) getActivity();
        this.data = gameActivity.getParameters();
        this.nickname = data.getString(String.valueOf(R.string.intent_options_nickname));
        this.num_columns = data.getInt(String.valueOf(R.string.intent_options_size));
        this.timer_status = data.getBoolean(String.valueOf(R.string.intent_options_timer));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Call the fragment layout
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        board = getView().findViewById(R.id.gridView);
        df = new SimpleDateFormat("HH:mm:ss");
        this.start_drop = df.format(Calendar.getInstance().getTime());
        //Set columns width
        this.setColumnWidth(board);
        //Start the game
        this.startGame();
        //Call the adapter to set the content of the grid view
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
            Toast.makeText(this.gameActivity, result, Toast.LENGTH_SHORT).show();
        }
        else if(game_instance.getStatus() == Status.PLAYER2_WINS) {
            result = "Has perdido!";
            Toast.makeText(this.gameActivity, result, Toast.LENGTH_SHORT).show();
        }
        else if(game_instance.getStatus() == Status.DRAW) {
            result = "Habeis empatado!";
            Toast.makeText(this.gameActivity, result, Toast.LENGTH_SHORT).show();
        }
        addToDatabase();
        Intent i = new Intent(this.gameActivity, ResultsActivity.class);
        i.putExtra(String.valueOf(R.string.intent_game_nickname), nickname);
        i.putExtra(String.valueOf(R.string.intent_game_size), num_columns);
        i.putExtra(String.valueOf(R.string.intent_game_timestatus), timer_status);
        i.putExtra(String.valueOf(R.string.intent_game_timevalue), timer.getText());
        i.putExtra(String.valueOf(R.string.intent_game_result), result);
        startActivity(i);
        gameActivity.finish();
    }

    private void setColumnWidth(GridView board) {
        //Set num columns and column width
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
        //Initialize the game logic instance
        game_instance = new Game(num_columns, num_columns, 4);
        //Turn image
        turn = getView().findViewById(R.id.imageView3);
        turn.setImageResource(R.drawable.turn_red);
        this.startTimer();
    }

    private void startTimer() {
        //Display timer
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
                    //Stop the game
                    game_instance.setTimeFinished();
                    result = "Has agotado el tiempo!";
                    addToDatabase();
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), ResultsActivity.class);
                    i.putExtra(String.valueOf(R.string.intent_game_nickname), nickname);
                    i.putExtra(String.valueOf(R.string.intent_game_size), num_columns);
                    i.putExtra(String.valueOf(R.string.intent_game_timestatus), true);
                    i.putExtra(String.valueOf(R.string.intent_game_timevalue), timer.getText());
                    i.putExtra(String.valueOf(R.string.intent_game_result), result);
                    startActivity(i);
                    gameActivity.finish();
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
            //Add data to db entry var
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
            //Insert the entry to the db
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
