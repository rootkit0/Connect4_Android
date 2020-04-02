package com.example.connect4;

public class Player {

    private static final char PLAYER1 = '1';
    private static final char PLAYER2 = '2';

    private final char id;

    private Player(char id) {
        this.id = id;
    }

    public static Player player1() {
        return new Player(PLAYER1);
    }

    public static Player player2() {
        return new Player(PLAYER2);
    }

    public boolean isEqualTo(Player other) {
        if(other == null) {
            return false;
        }
        else return this.id == other.id;
    }

    public boolean isPlayer1() {
        return this.id == PLAYER1;
    }

    public boolean isPlayer2() {
        return this.id == PLAYER2;
    }
}
