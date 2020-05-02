package com.example.connect4;

import android.widget.ImageView;
import java.util.Random;

public class Game {

    private final Board board;
    private final int toWin;
    private Status status;
    private int columns;

    public Game(int rows, int columns, int toWin) {
        this.board = new Board(rows, columns);
        this.toWin = toWin;
        this.status = Status.PLAYER1_PLAYS;
        this.columns = columns;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean canPlayColumn(int column) {
        return board.canPlayColumn(column);
    }

    public Move drop(int column, ImageView turn) {
        if(this.status == Status.PLAYER1_PLAYS) {
            int row = board.firstEmptyRow(column);
            this.board.cells[row][column] = 1;
            if(board.maxConnected(new Position(row, column)) == this.toWin) {
                this.status = Status.PLAYER1_WINS;
            }
            else if(!board.hasValidMoves()) {
                this.status = Status.DRAW;
            }
            else {
                toggleTurn();
                turn.setImageResource(R.drawable.turn_yellow);
            }
            return new Move(1,  new Position(row, column));
        }
        else {
            int row = board.firstEmptyRow(column);
            this.board.cells[row][column] = 2;
            if(board.maxConnected(new Position(row, column)) == this.toWin) {
                this.status = Status.PLAYER2_WINS;
            }
            else if(!board.hasValidMoves()) {
                this.status = Status.DRAW;
            }
            else {
                toggleTurn();
                turn.setImageResource(R.drawable.turn_red);
            }
            return new Move(2,  new Position(row, column));
        }
    }

    public int playOpponent() {
        Random rand = new Random();
        int random_colum = rand.nextInt(columns);
        boolean found_position = false;
        while(!found_position) {
            if(canPlayColumn(random_colum)) {
                found_position = true;
            }
            else {
                random_colum = rand.nextInt(columns);
            }
        }
        return random_colum;
    }

    public void toggleTurn() {
        if(getStatus() == Status.PLAYER1_PLAYS) {
            this.status = Status.PLAYER2_PLAYS;
        }
        else {
            this.status = Status.PLAYER1_PLAYS;
        }
    }

    public boolean checkForFinish() {
        if(this.status == Status.PLAYER1_WINS || this.status == Status.PLAYER2_WINS || this.status == Status.DRAW || this.status == Status.TIME_FINISHED) {
            return true;
        }
        return false;
    }

    public void setTimeFinished() {
        this.status = Status.TIME_FINISHED;
    }
}
