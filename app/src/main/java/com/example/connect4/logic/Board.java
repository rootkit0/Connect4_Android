package com.example.connect4.logic;

public class Board {
    private final int numRows;
    private final int numColumns;
    public final int[][] cells;

    public Board(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.cells = new int [numRows][numColumns];
    }

    public boolean canPlayColumn(int column) {
        if(column >= 0 && column < numColumns) {
            for(int i=0; i<numRows; ++i) {
                if(cells[i][column] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasValidMoves() {
        for(int i=0; i<this.numColumns; ++i) {
            if(canPlayColumn(i)) {
                return true;
            }
        }
        return false;
    }

    public int firstEmptyRow(int column) {
        if(column < 0 || column > this.numRows) {
            return -1;
        }
        if(canPlayColumn(column)) {
            for(int i=this.numRows-1; i>=0; --i){
                if(this.cells[i][column] == 0){
                    return i;
                }
            }
        }
        return -1;
    }

    public int maxConnected(Position position) {
        int player = this.cells[position.getRow()][position.getColumn()];
        return Math.max(maxConnectedVertical(position, player), Math.max(maxConnectedHorizontal(position, player), maxConnectedDiagonal(position, player)));
    }

    public int maxConnectedVertical(Position position, int player) {
        int count_v = 1;
        for(int i=position.getRow()+1; i<this.numRows; ++i) {
            if(this.cells[i][position.getColumn()] != player) {
                break;
            }
            ++count_v;
        }
        for(int i=position.getRow()-1; i>=0; --i) {
            if(this.cells[i][position.getColumn()] != player) {
                break;
            }
            ++count_v;
        }
        return count_v;
    }

    public int maxConnectedHorizontal(Position position, int player) {
        int count_h = 1;
        for(int i=position.getColumn()+1; i<this.numColumns; ++i) {
            if(this.cells[position.getRow()][i] != player) {
                break;
            }
            ++count_h;
        }
        for(int i=position.getColumn()-1; i>=0; --i) {
            if(this.cells[position.getRow()][i] != player) {
                break;
            }
            ++count_h;
        }
        return count_h;
    }

    public int maxConnectedDiagonal(Position position, int player) {
        int count_d = 1;
        for(int i=position.getRow()+1, j=position.getColumn()+1; i<this.numRows && j<this.numColumns && i>=0 && j>=0; ++i, ++j) {
            if(this.cells[i][j] != player) {
                break;
            }
            ++count_d;
        }
        for(int i=position.getRow()-1, j=position.getColumn()-1; i<this.numRows && j<this.numColumns && i>=0 && j>=0; --i, --j) {
            if(this.cells[i][j] != player) {
                break;
            }
            ++count_d;
        }
        return count_d;
    }
}
