package com.example.connect4.android;

import android.content.Intent;
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
import com.example.connect4.logic.Status;

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
        if(game_instance.checkForFinish()) {
            gameFinished();
        }
    }

    private void gameFinished() {
        String result = "";
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
                    Toast.makeText(getActivity(), "Has agotado el tiempo!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), ResultsActivity.class);
                    i.putExtra(String.valueOf(R.string.intent_game_nickname), nickname);
                    i.putExtra(String.valueOf(R.string.intent_game_size), num_columns);
                    i.putExtra(String.valueOf(R.string.intent_game_timestatus), true);
                    i.putExtra(String.valueOf(R.string.intent_game_timevalue), timer.getText());
                    i.putExtra(String.valueOf(R.string.intent_game_result), "Has agotado el tiempo!");
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
}
