package com.example.connect4;

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

    public Position move(Direction direction) {
        return new Position (getRow() + direction.getChangeInRow(), getColumn() + direction.getChangeInColumn());
    }

    public boolean isEqualTo(Position other) {
        return (this.getRow() == other.getRow()) && this.getColumn() == other.getColumn();
    }

    public static int pathLength(Position pos1, Position pos2) {
        int length = 0;
        if(pos1.row == pos2.row) {
            if(pos1.column >= pos2.column) {
                length = pos1.column - pos2.column;
            }
            else {
                length = pos2.column - pos1.column;
            }
        }
        else if(pos1.column == pos2.column) {
            if(pos1.row >= pos2.row) {
                length = pos1.row - pos2.row;
            }
            else {
                length = pos2.row - pos1.row;
            }
        }
        else {
            if(pos1.row >= pos2.row) {
                length = pos1.row - pos2.row;
            }
            else {
                length = pos2.row - pos1.row;
            }
        }
        return length + 1;
    }
}
