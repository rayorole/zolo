package com.kdg.zolo.mvp.model;

public class Move {

    private final int row;
    private final int col;
    private final Ship ship;
    private final boolean isHorizontal;

    public Move(int row, int col, Ship ship, boolean isHorizontal) {
        this.row = row;
        this.col = col;
        this.ship = ship;
        this.isHorizontal = isHorizontal;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }
}
