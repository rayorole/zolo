package com.kdg.zolo.mvp.model;

public class Ship {

    private final int length;
    private final String type;
    private boolean isPlaced;

    public Ship(int length, String type) {
        this.length = length;
        this.type = type;
        this.isPlaced = false;
    }

    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }
}
