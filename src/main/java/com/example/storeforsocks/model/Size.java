package com.example.storeforsocks.model;

public enum Size {
    R35(35), R35_5(35.5),R36(36), R36_5(36.5), R37(37), R37_5(37.5), R38(38), R38_5(38.5), R39(39),
    R39_5(39.5), R40(40), R40_5(40.5), R41(41), R41_5(41.5), R42(42), R42_5(42.5), R43(43), R43_5(43.5);

    private final double sizeNum;

    Size(double sizeNum) {
        this.sizeNum = sizeNum;
    }
}