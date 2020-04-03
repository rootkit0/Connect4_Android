package com.example.connect4;

public class Board {

    private final int numRows;
    private final int numColumns;
    private final Player[][] cells;

    public Board(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.cells = new Player[numRows][numColumns];
    }

    public boolean canPlayColumn(int column) {
        boolean canplay = false;
        if(column >= 0 && column < numColumns) {
            for(int i=0; i<numRows; ++i) {
                if(cells[i][column] == null) {
                    canplay = true;
                }
            }
        }
        return canplay;
    }

    public boolean hasValidMoves() {
        for(int i=0; i<this.numColumns; ++i) {
            if(canPlayColumn(i)) return true;
        }
        return false;
    }

    public Position occupyCell(int column, Player player) {
        if(canPlayColumn(column)) {
            if(player.isPlayer1()) {
                this.cells[firstEmptyRow(column)][column] = Player.player1();

            }
            else {
                this.cells[firstEmptyRow(column)][column] = Player.player2();
            }
            return new Position(firstEmptyRow(column), column);
        }
        else {
            if(player.isPlayer1()) {
                //Aviso por pantalla columna llena
            }
            return null;
        }
    }

    public int firstEmptyRow(int column) {
        if(column < 0 || column > this.numRows) return -1;
        if(canPlayColumn(column)) {
            for(int i=this.numRows-1; i>=0; --i){
                if(this.cells[i][column] == null) return i;
            }
        }
        return -1;
    }

    public int maxConnected(Position position) {
        Player player = this.cells[position.getRow()][position.getColumn()];
        return Math.max(maxConnectedVertical(position, player), Math.max(maxConnectedHorizontal(position, player), maxConnectedDiagonal(position, player)));
    }

    public int maxConnectedVertical(Position position, Player player) {
        int count_v = 1;
        for(int i=position.getRow()+1; i<this.numRows; ++i) {
            if(!player.isEqualTo(this.cells[i][position.getColumn()])) {
                break;
            }
            ++count_v;
        }
        for(int i=position.getRow()-1; i>=0; --i) {
            if(!player.isEqualTo(this.cells[i][position.getColumn()])) {
                break;
            }
            ++count_v;
        }
        return count_v;
    }

    public int maxConnectedHorizontal(Position position, Player player) {
        int count_h = 1;
        for(int i=position.getColumn()+1; i<this.numColumns; ++i) {
            if(!player.isEqualTo(this.cells[position.getRow()][i])) {
                break;
            }
            ++count_h;
        }
        for(int i=position.getColumn()-1; i>=0; --i) {
            if(!player.isEqualTo(this.cells[position.getRow()][i])) {
                break;
            }
            ++count_h;
        }
        return count_h;
    }

    public int maxConnectedDiagonal(Position position, Player player) {
        int count_d = 1;
        for(int i=position.getRow()+1, j=position.getColumn()+1; i<this.numRows && j<this.numColumns && i>=0 && j>=0; ++i, ++j) {
            if(!player.isEqualTo(this.cells[i][j])) {
                break;
            }
            ++count_d;
        }
        for(int i=position.getRow()-1, j=position.getColumn()-1; i<this.numRows && j<this.numColumns && i>=0 && j>=0; --i, --j) {
            if(!player.isEqualTo(this.cells[i][j])) {
                break;
            }
            ++count_d;
        }
        return count_d;
    }
}
