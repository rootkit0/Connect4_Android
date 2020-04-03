package com.example.connect4;
import java.util.Random;

public class Game {

    private final Board board;
    private final int toWin;
    private Status status;
    private boolean hasWinner;
    private int columns;

    public Game(int rows, int columns, int toWin) {
        this.board = new Board(rows, columns);
        this.toWin = toWin;
        this.status = Status.PLAYER1_PLAYS;
        this.hasWinner = false;
        this.columns = columns;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean canPlayColumn(int column) {
        return board.canPlayColumn(column);
    }

    public Move drop(int column) {
        if(this.status == Status.PLAYER1_PLAYS) {
            //Poner posicion seleccionada por el usuario
            Position pos1 = board.occupyCell(column, Player.player1());
            if(board.maxConnected(pos1) == this.toWin) {
                this.status = Status.PLAYER1_WINS;
            }
            else if(!board.hasValidMoves()) {
                this.status = Status.DRAW;
            }
            else {
                toggleTurn();
            }
            return new Move(Player.player1(), pos1);
        }
        else {
            Position pos1 = playOpponent();
            if(board.maxConnected(pos1) == this.toWin) {
                this.status = Status.PLAYER2_WINS;
            }
            else if(!board.hasValidMoves()) {
                this.status = Status.DRAW;
            }
            else {
                toggleTurn();
            }
            return new Move(Player.player2(), pos1);
        }
    }

    public Position playOpponent() {
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
        return board.occupyCell(random_colum, Player.player2());
    }

    public void toggleTurn() {
        if(getStatus() == Status.PLAYER1_PLAYS) {
            this.status = Status.PLAYER2_PLAYS;
        }
        else {
            this.status = Status.PLAYER1_PLAYS;
        }
    }

    public void manageTime() {
        //Temporizador
    }

    public boolean checkForFinish() {
        if(this.status == Status.PLAYER1_WINS || this.status == Status.PLAYER2_WINS || this.status == Status.DRAW) {
            return true;
        }
        return false;
    }
}
