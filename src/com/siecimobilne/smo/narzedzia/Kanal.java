package com.siecimobilne.smo.narzedzia;

/**
 * Klasa symulujaca kanal
 * Created by marcin on 16.12.15.
 */
public class Kanal {
    private boolean wolny = true;
    private int id;

    public Kanal(int id) {
        this.id = id;
    }

    public boolean isWolny() {
        return wolny;
    }
}
