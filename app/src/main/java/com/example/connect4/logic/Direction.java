package com.example.connect4.logic;

public class Direction {
    public static final Direction DOWN            = new Direction(1, 0);
    public static final Direction RIGHT           = new Direction(0, 1);
    public static final Direction MAIN_DIAGONAL   = new Direction(1, 1);
    public static final Direction CONTRA_DIAGONAL = new Direction(1, -1);

    public static final Direction[] ALL = new Direction[] {
            RIGHT, DOWN, MAIN_DIAGONAL, CONTRA_DIAGONAL
    };

    private final int changeInRow;
    private final int changeInColumn;

    private Direction(int changeInRow, int changeInColumn) {
        this.changeInRow = changeInRow;
        this.changeInColumn = changeInColumn;
    }

    public int getChangeInRow() {
        return changeInRow;
    }

    public int getChangeInColumn() {
        return changeInColumn;
    }

    public Direction invert() {
        Direction direction = new Direction(this.changeInRow*-1, this.changeInColumn*-1);
        return direction;
    }
}
