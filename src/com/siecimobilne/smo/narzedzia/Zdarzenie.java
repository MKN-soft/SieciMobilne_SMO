package com.siecimobilne.smo.narzedzia;

/**
 * Klasa przechowujaca informacje o danym zdarzeniu
 * Created by marcin on 16.12.15.
 */
class Zdarzenie {
    private int typ;
    private double czas;

    public Zdarzenie(int typ, double czas) {
        this.typ = typ;
        this.czas = czas;
    }

    public double getCzas() {
        return czas;
    }

    public int getTyp() {
        return typ;
    }
}
