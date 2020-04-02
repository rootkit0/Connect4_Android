package com.example.connect4;

public class Move {
    private final Player player;
    private final Position position;

    public Move(Player player, Position position) {
        this.player = player;
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }
}
