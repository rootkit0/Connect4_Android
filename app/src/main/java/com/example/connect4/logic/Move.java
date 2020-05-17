package com.example.connect4.logic;

public class Move {
    private final int player;
    private final Position position;

    public Move(int player, Position position) {
        this.player = player;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
