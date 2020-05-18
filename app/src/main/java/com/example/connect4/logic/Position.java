package com.example.connect4.logic;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    Position move(Direction direction) {
        return new Position(this.row + direction.getChangeInRow(), this.column + direction.getChangeInColumn());
    }

    boolean isEqualTo(Position other) {
        if(other == null){
            return false;
        }
        return this.row == other.row && this.column == other.column;
    }

    static int pathLength(Position pos1, Position pos2) {
        if (pos1.column==pos2.column) return Math.abs(pos1.row-pos2.row)+1;
        else return Math.abs(pos1.column-pos2.column)+1;
    }
}
