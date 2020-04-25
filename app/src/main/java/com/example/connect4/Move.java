package com.example.connect4;

public class Move {
    private final int player;
    private final Position position;

    public Move(int player, Position position) {
        this.player = player;
        this.position = position;
    }

    public int getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }
}
