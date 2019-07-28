package com.citytechware.idmanager.service;

public enum FingerprintPositions {
    RIGHT_THUMB(1), RIGHT_INDEX(2), RIGHT_MIDDLE(3), RIGHT_RING(4), RIGHT_LITTLE(5),
    LEFT_THUMB(6), LEFT_INDEX(7), LEFT_MIDDLE(8), LEFT_RING(9), LEFT_LITTLE(10);

    private final int index;

    FingerprintPositions(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
