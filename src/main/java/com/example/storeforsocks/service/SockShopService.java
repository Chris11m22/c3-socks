package com.example.storeforsocks.service;

import com.example.storeforsocks.model.Color;
import com.example.storeforsocks.model.Size;
import com.example.storeforsocks.model.Socks;

public interface SockShopService {
    void addSocks(Socks socks);

    Socks editSocksFromStock(Socks socks);

    int getSocks(Color color, Size size, int cottonMin, int cottonMax);

    boolean removeSocks(Socks socks);
}
