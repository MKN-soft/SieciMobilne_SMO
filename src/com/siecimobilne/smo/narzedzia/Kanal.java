package com.siecimobilne.smo.narzedzia;

/**
 * Klasa symulujaca kanal
 * Created by marcin on 16.12.15.
 */
public class Kanal {
    private boolean wolny = true;
    private int id;
    private Zdarzenie zdarzenie;

    public Kanal(int id) {
        this.id = id;
        zdarzenie  = null;
        this.wolny = true;
    }

    public void dodajZdarzenie(int typ, double czas){
        this.zdarzenie = new Zdarzenie(typ,czas);
        this.wolny = false;
    }

    public void usunZdarzenie(){
        this.zdarzenie = null;
        this.wolny = true;
    }
    public boolean isWolny() {
        return this.wolny;
    }

    public Zdarzenie getZdarzenie() {
        return zdarzenie;
    }

    public void setZdarzenie(Zdarzenie zdarzenie) {
        this.zdarzenie = zdarzenie;
        this.wolny = false;
    }
}
