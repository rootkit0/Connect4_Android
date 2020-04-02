package com.example.connect4;

public class Game {

    private final Board board;
    private final int toWin;

    private Status status;

    public Game(int rows, int columns, int toWin) {
        this.board = new Board(rows, columns);
        this.toWin = toWin;
        this.status = Status.PLAYER1_PLAYS;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean canPlayColumn(int column) {
        return board.canPlayColumn(column);
    }

    public Move play(int column) {
        if(this.status == Status.PLAYER1_PLAYS) {
            Position pos1 = board.play(column, Player.player1());
            if(board.maxConnected(pos1) == this.toWin) {
                this.status = Status.PLAYER1_WINS;
            }
            else if(!board.hasValidMoves()) {
                this.status = Status.DRAW;
            }
            else {
                this.status = Status.PLAYER2_PLAYS;
            }
            return new Move(Player.player1(), pos1);
        }
        else {
            Position pos1 = board.play(column, Player.player2());
            if(board.maxConnected(pos1) == this.toWin) {
                this.status = Status.PLAYER2_WINS;
            }
            else if(!board.hasValidMoves()) {
                this.status = Status.DRAW;
            }
            else {
                this.status = Status.PLAYER1_PLAYS;
            }
            return new Move(Player.player2(), pos1);
        }
    }

    public boolean isFinished() {
        if(this.status == Status.PLAYER1_WINS || this.status == Status.PLAYER2_WINS || this.status == Status.DRAW) {
            return true;
        }
        return false;
    }
}
